package ua.spro.util.test;

import static java.time.Month.*;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

    public class TableViewSampleWithBirthday extends Application {

        @Override
        public void start(Stage primaryStage) {
            final ObservableList<Person> data
                    = FXCollections.observableArrayList(
                    new Person("Jacob", "Smith", "jacob.smith@example.com", MonthDay.of(MARCH, 8)),
                    new Person("Isabella", "Johnson", "isabella.johnson@example.com", MonthDay.of(DECEMBER, 13)),
                    new Person("Ethan", "Williams", "ethan.williams@example.com", MonthDay.of(SEPTEMBER, 26)),
                    new Person("Emma", "Jones", "emma.jones@example.com", MonthDay.of(DECEMBER, 5)),
                    new Person("Michael", "Brown", "michael.brown@example.com", MonthDay.of(DECEMBER, 31))
            );
            final TableView<Person> table = new TableView<>();
            table.setItems(data);
            table.setEditable(true);
            TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
            firstNameCol.setMinWidth(100);
            firstNameCol.setCellValueFactory(
                    new PropertyValueFactory<>("firstName"));

            TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
            lastNameCol.setMinWidth(100);
            lastNameCol.setCellValueFactory(
                    new PropertyValueFactory<>("lastName"));

            TableColumn<Person, String> emailCol = new TableColumn<>("Email");
            emailCol.setMinWidth(200);
            emailCol.setCellValueFactory(
                    new PropertyValueFactory<>("email"));

            TableColumn<Person, MonthDay> birthdayCol = new TableColumn<>("Birthday");
            birthdayCol.setMinWidth(150);
            birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
            birthdayCol.setCellFactory(col -> new BirthdayCell());
            birthdayCol.setEditable(true);
            birthdayCol.setOnEditCommit(event -> event.getRowValue().setBirthday(event.getNewValue()));

            table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, birthdayCol);

            final Button showDataButton = new Button("Show data");
            showDataButton.setOnAction(event ->
                    table.getItems().stream()
                            .map(p -> String.format("%s %s (%s) %s", p.getFirstName(), p.getLastName(), p.getEmail(), p.getBirthday()))
                            .forEach(System.out::println)
            );

            final HBox controls = new HBox(5);
            controls.setAlignment(Pos.CENTER);
            controls.setPadding(new Insets(10));
            controls.getChildren().add(showDataButton);

            final Menu menu1 = new Menu("File");
            menu1.getItems().addAll(new MenuItem("New"), new MenuItem("Open"));
            final Menu menu2 = new Menu("Options");
            final Menu menu3 = new Menu("Help");

            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(menu1, menu2, menu3);
            menuBar.setUseSystemMenuBar(true);

            BorderPane root = new BorderPane(table, menuBar, null, controls, null);

            Scene scene = new Scene(root, 600, 400);


            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public  class BirthdayCell extends TableCell<Person, MonthDay> {

            private final DateTimeFormatter formatter ;
            private final DatePicker datePicker ;

            public BirthdayCell() {

                formatter = DateTimeFormatter.ofPattern("MMMM d") ;
                datePicker = new DatePicker() ;

                // Commit edit on Enter and cancel on Escape.
                // Note that the default behavior consumes key events, so we must
                // register this as an event filter to capture it.
                // Consequently, with Enter, the datePicker's value won't yet have been updated,
                // so commit will sent the wrong value. So we must update it ourselves from the
                // editor's text value.

                datePicker.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                    if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
                        datePicker.setValue(datePicker.getConverter().fromString(datePicker.getEditor().getText()));
                        commitEdit(MonthDay.from(datePicker.getValue()));
                    }
                    if (event.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                });

                // Modify default mouse behavior on date picker:
                // Don't hide popup on single click, just set date
                // On double-click, hide popup and commit edit for editor
                // Must consume event to prevent default hiding behavior, so
                // must update date picker value ourselves.

                // Modify key behavior so that enter on a selected cell commits the edit
                // on that cell's date.

                datePicker.setDayCellFactory(picker -> {
                    DateCell cell = new DateCell();
                    cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                        datePicker.setValue(cell.getItem());
                        if (event.getClickCount() == 2) {
                            datePicker.hide();
                            commitEdit(MonthDay.from(cell.getItem()));
                        }
                        event.consume();
                    });
                    cell.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                        if (event.getCode() == KeyCode.ENTER) {
                            commitEdit(MonthDay.from(datePicker.getValue()));
                        }
                    });
                    return cell ;
                });

                contentDisplayProperty().bind(Bindings.when(editingProperty())
                        .then(ContentDisplay.GRAPHIC_ONLY)
                        .otherwise(ContentDisplay.TEXT_ONLY));
            }

            @Override
            public void updateItem(MonthDay birthday, boolean empty) {
                super.updateItem(birthday, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(formatter.format(birthday));
                    setGraphic(datePicker);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();
                if (!isEmpty()) {
                    datePicker.setValue(getItem().atYear(LocalDate.now().getYear()));
                }
            }

        }

        public static class Person {

            private final StringProperty firstName;
            private final StringProperty lastName;
            private final StringProperty email;
            private final ObjectProperty<MonthDay> birthday ;

            public Person(String firstName, String lastName, String email, MonthDay birthday) {
                this.firstName = new SimpleStringProperty(this, "firstName", firstName);
                this.lastName = new SimpleStringProperty(this, "lastName", lastName);
                this.email = new SimpleStringProperty(this, "email", email);
                this.birthday = new SimpleObjectProperty<>(this, "birthday", birthday);
            }

            public String getFirstName() {
                return firstName.get();
            }

            public void setFirstName(String value) {
                firstName.set(value);
            }

            public StringProperty firstNameProperty() {
                return firstName;
            }

            public String getLastName() {
                return lastName.get();
            }

            public void setLastName(String value) {
                lastName.set(value);
            }

            public StringProperty lastNameProperty() {
                return lastName;
            }

            public String getEmail() {
                return email.get();
            }

            public void setEmail(String value) {
                email.set(value);
            }

            public StringProperty emailProperty() {
                return email;
            }

            public MonthDay getBirthday() {
                return birthday.get();
            }

            public void setBirthday(MonthDay birthday) {
                this.birthday.set(birthday);
            }

            public ObjectProperty<MonthDay> birthdayProperty() {
                return birthday ;
            }

        }

        public static void main(String[] args) {
            launch(args);
        }

    }

