package net.javaguides.springboot.helper;

import javassist.bytecode.DuplicateMemberException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelUpload {

    public static boolean isValidExcelFile(MultipartFile file){

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );

    }
    public static List<Employee> excelToEmployees(InputStream inputStream) {

        List<Employee> employees = new ArrayList<Employee>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("employees");
            DataFormatter formatter = new DataFormatter();
            int rowIndex = 0;

            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Employee employee = new Employee();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String strValue = formatter.formatCellValue(cell);
                    switch (cellIndex) {
                        case 0:
//                            if(employeeRepository.existsById(Integer.parseInt(strValue))) {
//                                throw new RuntimeException("Id is duplicate , please change id");
//                            }
                            employee.setId(Integer.parseInt(strValue));
//                            employee.setId((int) cell.getNumericCellValue());
                            break;

                        case 1:
//                            employee.setFname(cell.getStringCellValue());
                            employee.setFname(strValue);
                            break;

                        case 2:
//                            employee.setLname(cell.getStringCellValue());
                            employee.setLname(strValue);
                            break;

                        case 3:
//                            employee.setGender(cell.getStringCellValue());
                            employee.setGender(strValue);
                            break;
                        case 4:
//                            employee.setDob(cell.getStringCellValue());
                            employee.setDob(strValue);
                            break;
                        case 5:
//                            employee.setAddress(cell.getStringCellValue());
                            employee.setAddress(strValue);
                            break;
                        case 6:
//                            employee.setPhone(cell.getStringCellValue());
                            employee.setPhone(strValue);
                            break;
                        case 7:
//                            employee.setEmail(cell.getStringCellValue());
                            employee.setEmail(strValue);
                            break;
//                        case 8:
//                            employee.setPassword(strValue);
//                            break;
                        case 8:
                            employee.setPositionId(Integer.parseInt(strValue));
//                            employee.setPositionId((int) cell.getNumericCellValue());
                            break;
                        case 9:
                            employee.setDepartmentId(Integer.parseInt(strValue));
//                            employee.setDepartmentId((int) cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                employees.add(employee);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return employees;
    }
}