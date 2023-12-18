package com.bankingapi.service;

import com.bankingapi.model.Transaction;
import com.bankingapi.model.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GeneratePdf {
    @Value("${csv.file.directory}")
    private String csvFileDirectory;

    public void makingPdf(User user) throws FileNotFoundException, ParseException {
        String csvFilePath=csvFileDirectory + File.separator +"banking.csv";
//        List<Transaction> beans=new CsvToBeanBuilder(new FileReader(csvFilePath))
//                .withType(Transaction.class)
//                .build()
//                .parse();
        FileReader filereader = new FileReader(csvFilePath);
        List<String[]> beans;
        try (CSVReader reader = new CSVReaderBuilder(filereader)
                .withSkipLines(1)
                .build() ) {
            beans = reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat fDate= new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate=fDate.parse(user.getFromDate());
        SimpleDateFormat tDate= new SimpleDateFormat("dd/MM/yyyy");
        Date toDate=fDate.parse(user.getToDate());

        System.out.println(fromDate);
        System.out.println(toDate);
        Document doc = new Document(PageSize.A4);
        PdfWriter writer=PdfWriter.getInstance(doc,new FileOutputStream("invoice.pdf"));        doc.open();
        PdfContentByte cb = writer.getDirectContent();
        cb.moveTo(0, 0);
        Font title = FontFactory.getFont(FontFactory.HELVETICA, 30);
        Paragraph titlepara = new Paragraph("invoice", title);
        titlepara.setAlignment(Element.ALIGN_CENTER);
        doc.add(titlepara);
        doc.add(new Paragraph("\n\n"));
        doc.add(new Paragraph("\n\n"));

        doc.add(new Paragraph("\n\n"));

        Paragraph userid = new Paragraph(user.getUserid());
        doc.add(userid);
        doc.add(new Paragraph("\n\n"));
        Table table = new Table(2);
        table.addCell("Date");
        table.addCell("amount");
        //System.out.println(beans.get(0).getAmount());
        int i=0;
        for(String[] x:beans){
            Transaction t=new Transaction(x[0],x[1],x[2]);
            System.out.println(t.getUserId());
            System.out.println(t.getAmount());
            System.out.println(t.getDate());
            if( (fromDate==null || fromDate.before(t.getDate()) || fromDate.equals(t.getDate()))
                    &&(toDate==null || toDate.after(t.getDate()))&&
                    user.getUserid().equals(t.getUserId()) )
            {
                System.out.println("yash");
                table.addCell(t.getDate().toString());
                table.addCell(t.getAmount());

            }

        }
        doc.add(table);
        doc.close();

    }

}
