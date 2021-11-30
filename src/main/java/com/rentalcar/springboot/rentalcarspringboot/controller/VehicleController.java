package com.rentalcar.springboot.rentalcarspringboot.controller;

import com.rentalcar.springboot.rentalcarspringboot.model.Category;
import com.rentalcar.springboot.rentalcarspringboot.model.Vehicle;
import com.rentalcar.springboot.rentalcarspringboot.service.CategoryService;
import com.rentalcar.springboot.rentalcarspringboot.service.VehicleService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin(origins = "http://localhost:4200")
public class VehicleController {
    private VehicleService vehicleService;
    private CategoryService categoryService;

    public VehicleController(VehicleService vehicleService, CategoryService categoryService) {
        this.vehicleService = vehicleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Vehicle>> getVehicles() {
        try {
            List<Vehicle> vehicles = vehicleService.findAll();

            if (vehicles.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(vehicles, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id") int id) {
        return new ResponseEntity<>(vehicleService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-by-category/{id}")
    public ResponseEntity<List<Vehicle>> getVehiclesByCategory(@PathVariable("id") int idCategory) {
        try {
            Category category = categoryService.findById(idCategory);
            List<Vehicle> vehicles = vehicleService.findByCategory(category);

            if (vehicles.isEmpty()) {
                return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
            }

            return new ResponseEntity<>(vehicles, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/edit")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle) {
        try {
            vehicleService.updateVehicle(vehicle);
            return new ResponseEntity<>(vehicle, new HttpHeaders(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/vehicles-from-file")
    public ResponseEntity insertVehicles(@RequestParam("file") MultipartFile file) {
        try
        {
            InputStream initialStream = file.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            File targetFile = new File("src/main/resources/targetFile.tmp");

            try (OutputStream outStream = new FileOutputStream(targetFile)) {
                outStream.write(buffer);
            }

            Vehicle vehicle = new Vehicle();
            XSSFWorkbook wb = new XSSFWorkbook(targetFile);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            Row row = itr.next();

            while (itr.hasNext()) {
                row = itr.next();
                Cell cellModel = row.getCell(0);
                String model = cellModel.getStringCellValue();
                Cell cellManufacturer = row.getCell(1);
                String manufacturer = cellManufacturer.getStringCellValue();
                Cell cellPlate = row.getCell(2);
                String plate = cellPlate.getStringCellValue();
                Cell cellYear = row.getCell(3);
                int year = (int) cellYear.getNumericCellValue();
                Cell cellCategory = row.getCell(4);
                String category = cellCategory.getStringCellValue();

                Category cat = categoryService.findByTypology(category);

                vehicle.setModel(model);
                vehicle.setManufacturer(manufacturer);
                vehicle.setLicensePlate(plate);
                vehicle.setYearOfRegistration(year);
                vehicle.setCategory(cat);

                vehicleService.updateVehicle(vehicle);
            }
            wb.close();
            targetFile.delete();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity(new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Vehicle> deleteVehicle(@PathVariable("id") int id) {
        try {
            vehicleService.deleteVehicle(id);
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
