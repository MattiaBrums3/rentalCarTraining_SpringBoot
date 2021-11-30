package com.rentalcar.springboot.rentalcarspringboot.controller;

import com.rentalcar.springboot.rentalcarspringboot.model.Rental;
import com.rentalcar.springboot.rentalcarspringboot.model.User;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;
import com.rentalcar.springboot.rentalcarspringboot.service.RentalService;
import com.rentalcar.springboot.rentalcarspringboot.service.UserService;
import com.rentalcar.springboot.rentalcarspringboot.service.VehicleService;
import jdk.jfr.ContentType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/rentals")
@CrossOrigin(origins = "http://localhost:4200")
public class RentalController {
    private RentalService rentalService;
    private UserService userService;
    private VehicleService vehicleService;

    public RentalController(RentalService rentalService, UserService userService, VehicleService vehicleService) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Rental>> getRentals() {
        try {
            List<Rental> rentals = rentalService.findAll();

            if (rentals.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rentals, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") int id) {
        return new ResponseEntity<>(rentalService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-by-user/{id}")
    public ResponseEntity<List<Rental>> getRentalsByUser(@PathVariable("id") int id) {
        try {
            User user = userService.findById(id);
            List<Rental> rentals = rentalService.findByUser(user);

            if (rentals.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rentals, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-by-vehicle/{id}")
    public ResponseEntity<List<Rental>> getRentalsByVehicle(@PathVariable("id") int id) {
        try {
            Vehicle vehicle = vehicleService.findById(id);
            List<Rental> rentals = rentalService.findByVehicle(vehicle);

            if (rentals.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rentals, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/edit")
    public ResponseEntity<Rental> updateRental(@RequestBody Rental rental) {
        try {
            rentalService.updateRental(rental);
            return new ResponseEntity<>(rental, new HttpHeaders(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/post/download-rentals/{id}")
    @ResponseBody
    public ResponseEntity downloadRentals(@PathVariable int id) {
        try {
            User user = userService.findById(id);
            List<Rental> rentals = rentalService.findByUser(user);

            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Rentals");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);

            Row header = sheet.createRow(0);

            //Header Customization
            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            //Header
            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("idPrenotazione");
            headerCell.setCellStyle(headerStyle);
            headerCell = header.createCell(1);
            headerCell.setCellValue("Utente");
            headerCell.setCellStyle(headerStyle);
            headerCell = header.createCell(2);
            headerCell.setCellValue("Veicolo");
            headerCell.setCellStyle(headerStyle);
            headerCell = header.createCell(3);
            headerCell.setCellValue("Data di Inizio");
            headerCell.setCellStyle(headerStyle);
            headerCell = header.createCell(4);
            headerCell.setCellValue("Data di Fine");
            headerCell.setCellStyle(headerStyle);
            headerCell = header.createCell(5);
            headerCell.setCellValue("Approvato");
            headerCell.setCellStyle(headerStyle);

            //Content
            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            for(int i=0;i<rentals.toArray().length;i++) {
                Row row = sheet.createRow(i+1);
                Cell cell = row.createCell(0);
                cell.setCellValue(rentals.get(i).getId());
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(rentals.get(i).getUser().getName() + " " +
                        rentals.get(i).getUser().getSurname());
                cell.setCellStyle(style);
                cell = row.createCell(2);
                cell.setCellValue(rentals.get(i).getVehicle().getModel());
                cell.setCellStyle(style);
                cell = row.createCell(3);
                cell.setCellValue(rentals.get(i).getDateOfStart().toString());
                cell.setCellStyle(style);
                cell = row.createCell(4);
                cell.setCellValue(rentals.get(i).getDateOfEnd().toString());
                cell.setCellStyle(style);
                cell = row.createCell(5);
                if (rentals.get(i).getApproved()) {
                    cell.setCellValue("Sì");
                } else {
                    cell.setCellValue("No");
                }
                cell.setCellStyle(style);
            }

            //File storage
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();

            Path filePath = Paths.get(fileLocation);
            Resource file = new UrlResource(filePath.toUri());
            Path p = file.getFile().toPath();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(p));
            headers.add("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"");

            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Rental> deleteRental(@PathVariable("id") int id) {
        try {
            rentalService.deleteRental(id);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
