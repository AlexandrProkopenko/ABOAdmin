package ua.spro.util;

import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.spro.entity.Client;
import ua.spro.entity.Department;
import ua.spro.entity.History;
import ua.spro.entity.Status;
import ua.spro.service.impl.ClientServiceImpl;
import ua.spro.service.impl.DepartmentServiceImpl;
import ua.spro.service.impl.HistoryServiceImpl;
import ua.spro.service.impl.StatusServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Formatter;

public class ReadExcelUtil {

    private ClientServiceImpl clientService;
    private HistoryServiceImpl historyService;
    private DepartmentServiceImpl departmentService;
    private StatusServiceImpl statusService;
    private Client currentClient;
    private History currentHistory;
    private int currentDepartmentId;
    private Status currentStatus;
    private ObservableList<Status> statusList;

    public ReadExcelUtil(ClientServiceImpl clientService, HistoryServiceImpl historyService, DepartmentServiceImpl departmentService) {
        this.clientService = clientService;
        this.historyService = historyService;
        this.departmentService = departmentService;
        statusService = new StatusServiceImpl();
        currentStatus = new Status("");
    }

    public boolean readExcel(File excelFile){
//        File excelFile = new File("Контакти АБО Дитяча телешкола.xls");
        statusList = statusService.getAll();
        try(FileInputStream fileInputStream = new FileInputStream(excelFile)){
            Workbook wb = new XSSFWorkbook(fileInputStream);
            for (Sheet sheet: wb  ) {
                currentDepartmentId = wb.getSheetIndex(sheet.getSheetName())+1;
                departmentService.save(new Department(currentDepartmentId, wb.getSheetName(currentDepartmentId-1)));
                readSheet(sheet);
            }
            departmentService.save(new Department("Всі"));
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }





    private void readSheet(Sheet sheet){

        for(int i =9; i < sheet.getLastRowNum(); i++){
            Row row = sheet.getRow(i);
            String childName = "-";
            Double ageDouble = 0.0;
            String parentName = "-";
            String phone = "-";
            String location = "-";
            StringBuilder comment = new StringBuilder();
            String result = null;

            if (row!=null) {
                for (int j = 3; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);

                    if (cell != null) {
                        switch (cell.getCellTypeEnum()){
                            case STRING:
                                result = cell.getRichStringCellValue().toString();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    Formatter fmt = new Formatter();
                                    result = fmt.format("%tF", cell.getDateCellValue()).toString();
                                } else {
                                    int num = (int)cell.getNumericCellValue();
                                    result = num + "";
                                }
                                break;
                            case BLANK:
                                result = "-";
                                break;
                                default:
                                    System.out.println(" Непрочитана клітинка типу:  "+ cell.getCellTypeEnum());
                        }

                        switch (j) {
                            case 3:
                                childName = result;
                                break;
                            case 4:
                                ageDouble = parseAge(result);
                                break;
                            case 5:
                                break;
                            case 6:
                                parentName = extractText(result);
                                phone = extractPhoneNumber(result);
                                break;
                            case 7:
                                System.out.println("Зчитано строку   result (status)" + result );
                                currentStatus.setClientStatus(result);
                                if(statusList.contains(currentStatus)){
                                    currentStatus.setStatusId(statusList.indexOf(currentStatus)+1);
                                }
                                if (currentStatus.getStatusId() == null){
                                    currentStatus.setStatusId(1);
                                }
                                System.out.println("Присвоєно statusId: " + currentStatus.getStatusId());
                                break;
                            case 8:
                                comment.append(result + " ");
                                break;
                            case 9:
                                comment.append(result + " ");
                                break;
                            case 10:
                                comment.append(result + " ");
                                break;
                            case 11:
                                comment.append(result + " ");
                                break;
                            case 12:
                                location = result;
                                break;
                        }
                    }
                }
               if (!phone.equals("-")) {

                   String com = comment.toString();
                   if(com.length()>=255) {
                       com = com.substring(0, 254);
                   }
                    if(childName.length() >= 30) childName = childName.substring(0, 29);
                    if(parentName.length() >= 30) parentName = parentName.substring(0, 29);
                    if(location.length() >= 30) location = location.substring(0, 29);

                    currentClient = new Client(childName, ageDouble,parentName, phone, location, currentDepartmentId, currentStatus.getStatusId() );
                    currentStatus.setStatusId(null);
                    currentHistory = new History(LocalDateTime.now(),com);
                    clientService.saveClientAndHistory(currentClient, currentHistory);

               }
            }
        }
    }

    private Double parseAge(String ageString){
        if(ageString.equals("")) return 0.0;
        String src = extractAge(ageString);
//        System.out.println(src);
        if(src.equals("")) return 0.0;
        Double result = 0.0;
        try {
            result = Double.parseDouble(src);
        }catch (NumberFormatException e){
            System.out.println("Неможливо прочитати вік як число");
            e.printStackTrace();
        }
        return result;
    }


    private String extractPhoneNumber(String src){
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        if(builder.toString().length() < 10) return "-";

        if( builder.toString().indexOf("0") == 0){
            builder.insert(0, "+38 ").
                    insert(7, " ").
                    insert(11, " ").
                    insert(14, " ");
        }else
        {
            builder.insert(0, "+").
                    insert(3, " ").
                    insert(7, " ").
                    insert(11, " ").
                    insert(14, " ");
        }

        return builder.toString().substring(0, 17);
    }


    public String extractAge(String src){
        src = src.trim();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
           char c = src.charAt(i);
            if (Character.isDigit(c) || c == ',' || c  == '.') {
                if (c == ',') c = '.';
                builder.append(c);
            }
        }
        if (builder.toString().length() >4)
        return builder.toString().substring(0, 3);

        return builder.toString();
    }

    private String extractText(String src) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (Character.isAlphabetic(c) || Character.isSpaceChar(c)) {
                builder.append(c);

            }
        }
        return builder.toString().trim();
    }

}

