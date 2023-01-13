package com.fedorenko.service;

import com.fedorenko.model.*;
import com.fedorenko.repositry.InvoiceRepository;
import com.fedorenko.util.GetRandomTechnics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class ShopServiceTest {

    private ShopService target;
    private InvoiceRepository repository;
    private Invoice invoice;
    private static final String FILE_NAME = "data.csv";
    private Customer customer;


    @BeforeEach
    void setUp() {
        repository = InvoiceRepository.getInstance();
        repository.clear();
        target = ShopService.getInstance();
        customer = new PersonService().generateCustomer();
        invoice = new Invoice(customer);

    }

    @Test
    void getAll_positive() {
        List<Invoice> expected = new ArrayList<>();
        final Invoice invoice1 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice2 = target.dataToInvoice(FILE_NAME);
        expected.add(invoice1);
        expected.add(invoice2);
        Assertions.assertEquals(expected, target.getAllInvoices());
    }

    @Test
    void getAll_negative() {
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice);
        Assertions.assertNotEquals(expected, target.getAllInvoices());
    }

    @Test
    void getTypeInfo_positive() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        technics1.setTechnicsType(TechnicsType.TELEPHONE);
        technics2.setTechnicsType(TechnicsType.TELEPHONE);
        technics3.setTechnicsType(TechnicsType.TELEVISION);
        final Invoice invoice = new Invoice(customer);
        invoice.addProduct(technics1);
        invoice.addProduct(technics2);
        invoice.addProduct(technics3);
        repository.addInvoice(invoice);
        Map<TechnicsType, Integer> expected = new HashMap<>();
        expected.put(TechnicsType.TELEPHONE, 2);
        expected.put(TechnicsType.TELEVISION, 1);

        Assertions.assertEquals(expected.get(TechnicsType.TELEPHONE),
                target.getTypeInfo(repository.getAll()).get(TechnicsType.TELEPHONE).intValue());
    }

    @Test
    void getTypeInfo_negative() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        technics1.setTechnicsType(TechnicsType.TELEPHONE);
        technics2.setTechnicsType(TechnicsType.TELEPHONE);
        technics3.setTechnicsType(TechnicsType.TELEVISION);
        final Invoice invoice = new Invoice(customer);
        invoice.addProduct(technics1);
        invoice.addProduct(technics2);
        invoice.addProduct(technics3);
        repository.addInvoice(invoice);
        final int expected = -5;

        Assertions.assertNotEquals(expected,
                target.getTypeInfo(repository.getAll()).get(TechnicsType.TELEPHONE));
    }

    @Test
    void minInvoice_positive() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        technics1.setPrice(2000);
        technics2.setPrice(1500);
        technics3.setPrice(1000);
        final Invoice invoice1 = new Invoice(customer);
        final Invoice invoice2 = new Invoice(customer);
        final Invoice lowestPrice = new Invoice(customer);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(lowestPrice);
        invoice1.addProduct(technics1);
        invoice2.addProduct(technics2);
        lowestPrice.addProduct(technics3);

        Assertions.assertEquals(1000, target.minInvoice(repository.getAll()));
    }

    @Test
    void minInvoice_negative() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        technics1.setPrice(2000);
        technics2.setPrice(1500);
        technics3.setPrice(1000);
        final Invoice invoice1 = new Invoice(customer);
        final Invoice invoice2 = new Invoice(customer);
        final Invoice lowestPrice = new Invoice(customer);
        invoice1.addProduct(technics1);
        invoice2.addProduct(technics2);
        lowestPrice.addProduct(technics3);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(lowestPrice);

        Assertions.assertNotEquals(0, target.minInvoice(repository.getAll()));
    }

    @Test
    void allInvoiceSum_positive() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        technics1.setPrice(2000);
        technics2.setPrice(1500);
        technics3.setPrice(1000);
        final Invoice invoice1 = new Invoice(customer);
        final Invoice invoice2 = new Invoice(customer);
        final Invoice lowestPrice = new Invoice(customer);
        invoice1.addProduct(technics1);
        invoice2.addProduct(technics2);
        lowestPrice.addProduct(technics3);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(lowestPrice);

        Assertions.assertEquals(4500, target.allInvoiceSum(repository.getAll()));
    }

    @Test
    void allInvoiceSum_negative() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        technics1.setPrice(2000);
        technics2.setPrice(1500);
        technics3.setPrice(1000);
        final Invoice invoice1 = new Invoice(customer);
        final Invoice invoice2 = new Invoice(customer);
        final Invoice lowestPrice = new Invoice(customer);
        invoice1.addProduct(technics1);
        invoice2.addProduct(technics2);
        lowestPrice.addProduct(technics3);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(lowestPrice);

        Assertions.assertNotEquals(-400, target.allInvoiceSum(repository.getAll()));
    }

    @Test
    void retailInvoices_positive() {
        final Invoice invoice1 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice2 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice3 = target.dataToInvoice(FILE_NAME);
        invoice1.setInvoiceType(InvoiceType.RETAIL);
        invoice2.setInvoiceType(InvoiceType.RETAIL);
        invoice3.setInvoiceType(InvoiceType.WHOLESALE);

        Assertions.assertEquals(2, target.retailInvoices(repository.getAll()));
    }

    @Test
    void retailInvoices_negative() {
        final Invoice invoice1 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice2 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice3 = target.dataToInvoice(FILE_NAME);
        invoice1.setInvoiceType(InvoiceType.RETAIL);
        invoice1.setInvoiceType(InvoiceType.RETAIL);
        invoice1.setInvoiceType(InvoiceType.WHOLESALE);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(invoice3);

        Assertions.assertNotEquals(1, target.retailInvoices(repository.getAll()));
    }

    @Test
    void oneTypeInvoices_positive() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        technics1.setTechnicsType(TechnicsType.TELEPHONE);
        technics2.setTechnicsType(TechnicsType.TELEVISION);
        technics3.setTechnicsType(TechnicsType.TELEPHONE);
        final Invoice invoice1 = new Invoice(customer);
        final Invoice invoice2 = new Invoice(customer);
        final Invoice invoice3 = new Invoice(customer);
        invoice1.addProduct(technics1);
        invoice1.addProduct(technics3);
        invoice2.addProduct(technics2);
        invoice2.addProduct(technics1);
        invoice3.addProduct(technics3);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(invoice3);

        Assertions.assertEquals(2, target.oneTypeInvoices(repository.getAll()).size());
    }

    @Test
    void oneTypeInvoices() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        technics1.setTechnicsType(TechnicsType.TELEPHONE);
        technics2.setTechnicsType(TechnicsType.TELEVISION);
        technics3.setTechnicsType(TechnicsType.TELEPHONE);
        final Invoice invoice1 = new Invoice(customer);
        final Invoice invoice2 = new Invoice(customer);
        final Invoice invoice3 = new Invoice(customer);
        invoice1.addProduct(technics1);
        invoice1.addProduct(technics3);
        invoice2.addProduct(technics2);
        invoice2.addProduct(technics1);
        invoice3.addProduct(technics3);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(invoice3);

        Assertions.assertNotEquals(1, target.oneTypeInvoices(repository.getAll()).size());
    }

    @Test
    void firstThreeInvoices_positive() {
        final Invoice invoice1 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice2 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice3 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice4 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice5 = target.dataToInvoice(FILE_NAME);
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice2);
        expected.add(invoice3);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(invoice3);
        repository.addInvoice(invoice4);
        repository.addInvoice(invoice5);

        Assertions.assertEquals(expected, target.firstThreeInvoices(repository.getAll()));
    }

    @Test
    void firstThreeInvoices_negative() {
        final Invoice invoice1 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice2 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice3 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice4 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice5 = target.dataToInvoice(FILE_NAME);
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice2);
        expected.add(invoice4);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(invoice3);
        repository.addInvoice(invoice4);
        repository.addInvoice(invoice5);

        Assertions.assertNotEquals(expected, target.firstThreeInvoices(repository.getAll()));
    }

    @Test
    void lessThanEighteenInvoice_positive() {
        final Invoice invoice1 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice2 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice3 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice4 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice5 = target.dataToInvoice(FILE_NAME);
        invoice1.getCustomer().setAge(15);
        invoice2.getCustomer().setAge(128);
        invoice3.getCustomer().setAge(18);
        invoice4.getCustomer().setAge(11);
        invoice5.getCustomer().setAge(17);
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice4);
        expected.add(invoice5);

        Assertions.assertEquals(expected, target.lessThanEighteenInvoice(repository.getAll()));
    }

    @Test
    void lessThanEighteenInvoice() {
        final Invoice invoice1 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice2 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice3 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice4 = target.dataToInvoice(FILE_NAME);
        final Invoice invoice5 = target.dataToInvoice(FILE_NAME);
        invoice1.getCustomer().setAge(15);
        invoice2.getCustomer().setAge(128);
        invoice3.getCustomer().setAge(18);
        invoice4.getCustomer().setAge(11);
        invoice5.getCustomer().setAge(17);
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice2);
        expected.add(invoice3);

        Assertions.assertNotEquals(expected, target.lessThanEighteenInvoice(repository.getAll()));
    }

    @Test
    void sortInvoices_positive() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        final Invoice invoice1 = new Invoice(customer);
        final Invoice invoice2 = new Invoice(customer);
        final Invoice invoice3 = new Invoice(customer);
        final Invoice invoice4 = new Invoice(customer);
        final Invoice invoice5 = new Invoice(customer);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(invoice3);
        repository.addInvoice(invoice4);
        repository.addInvoice(invoice5);
        invoice1.getCustomer().setAge(90);
        invoice2.getCustomer().setAge(90);
        invoice3.getCustomer().setAge(90);
        invoice4.getCustomer().setAge(67);
        invoice5.getCustomer().setAge(50);

        invoice1.addProduct(technics1);
        invoice1.addProduct(technics2);
        invoice1.addProduct(technics3);
        invoice2.addProduct(technics2);
        invoice2.addProduct(technics2);
        invoice3.addProduct(technics3);
        invoice3.addProduct(technics3);

        technics2.setPrice(1000);
        technics3.setPrice(5000);

        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice3);
        expected.add(invoice2);
        expected.add(invoice4);
        expected.add(invoice5);

        Assertions.assertEquals(expected, target.sortInvoices(repository.getAll()));
    }

    @Test
    void sortInvoices_positive_negative() {
        final Technics technics1 = GetRandomTechnics.get();
        final Technics technics2 = GetRandomTechnics.get();
        final Technics technics3 = GetRandomTechnics.get();
        final Invoice invoice1 = new Invoice(customer);
        final Invoice invoice2 = new Invoice(customer);
        final Invoice invoice3 = new Invoice(customer);
        final Invoice invoice4 = new Invoice(customer);
        final Invoice invoice5 = new Invoice(customer);
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(invoice3);
        repository.addInvoice(invoice4);
        repository.addInvoice(invoice5);
        invoice1.getCustomer().setAge(90);
        invoice2.getCustomer().setAge(90);
        invoice3.getCustomer().setAge(90);
        invoice4.getCustomer().setAge(67);
        invoice5.getCustomer().setAge(50);

        invoice1.addProduct(technics1);
        invoice1.addProduct(technics2);
        invoice1.addProduct(technics3);
        invoice2.addProduct(technics2);
        invoice2.addProduct(technics2);
        invoice3.addProduct(technics3);
        invoice3.addProduct(technics3);

        technics2.setPrice(1000);
        technics3.setPrice(5000);

        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice5);
        expected.add(invoice1);
        expected.add(invoice2);
        expected.add(invoice3);
        expected.add(invoice4);

        Assertions.assertNotEquals(expected, target.sortInvoices(repository.getAll()));
    }
}