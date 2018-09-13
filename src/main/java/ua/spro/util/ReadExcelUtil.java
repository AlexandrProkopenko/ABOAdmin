package ua.spro.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import ua.spro.entity.Client;
import ua.spro.entity.Department;
import ua.spro.entity.History;
import ua.spro.service.impl.ClientServiceImpl;
import ua.spro.service.impl.DepartmentServiceImpl;
import ua.spro.service.impl.HistoryServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Formatter;

public class ReadExcelUtil {

    private ClientServiceImpl clientService;
    private HistoryServiceImpl historyService;
    private DepartmentServiceImpl departmentService;
    private Client currentClient;
    private History currentHistory;
    private int currentDepartmentId;

    public ReadExcelUtil(ClientServiceImpl clientService, HistoryServiceImpl historyService, DepartmentServiceImpl departmentService) {
        this.clientService = clientService;
        this.historyService = historyService;
        this.departmentService = departmentService;
    }

    public boolean readExcel(){
        File excelFile = new File("Контакти АБО Дитяча телешкола.xls");
        try(FileInputStream fileInputStream = new FileInputStream(excelFile)){
            Workbook wb = new HSSFWorkbook(fileInputStream);
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
                                comment.append(result + " ");
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
                   if(com.length()>255) {
                       com = com.substring(255);
                   }
                    if(childName.length() > 30) childName = childName.substring(30);
                    if(parentName.length() > 30) parentName = parentName.substring(30);
                    if(location.length() > 30) location = location.substring(30);

                    currentClient = new Client(childName, ageDouble,parentName, phone, location, currentDepartmentId, 1 );

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


    private String extractAge(String src){
        src = src.trim();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
           char c = src.charAt(i);
            if (Character.isDigit(c) || c == ',') {
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

