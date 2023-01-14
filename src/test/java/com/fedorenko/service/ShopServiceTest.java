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

    private static PersonService personService;
    private static Technics technics1;
    private static Technics technics2;
    private static Technics technics3;
    private static Invoice invoice1;
    private static Invoice invoice2;
    private static Invoice invoice3;
    private static Invoice invoice4;
    private static Invoice invoice5;




    @BeforeEach
    void setUp() {
        repository = InvoiceRepository.getInstance();
        repository.clear();
        target = ShopService.getInstance();
        personService = new PersonService();
        technics1 = GetRandomTechnics.get();
        technics2 = GetRandomTechnics.get();
        technics3 = GetRandomTechnics.get();
        invoice1 = new Invoice(personService.generateCustomer());
        invoice2 = new Invoice(personService.generateCustomer());
        invoice3 = new Invoice(personService.generateCustomer());
        invoice4 = new Invoice(personService.generateCustomer());
        invoice5 = new Invoice(personService.generateCustomer());
        repository.addInvoice(invoice1);
        repository.addInvoice(invoice2);
        repository.addInvoice(invoice3);
        repository.addInvoice(invoice4);
        repository.addInvoice(invoice5);

    }

    @Test
    void getAll_positive() {
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice2);
        expected.add(invoice3);
        expected.add(invoice4);
        expected.add(invoice5);
        Assertions.assertEquals(expected, target.getAllInvoices());
    }

    @Test
    void getAll_negative() {
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice2);
        expected.add(invoice3);
        expected.add(invoice4);

        Assertions.assertNotEquals(expected, target.getAllInvoices());
    }

    @Test
    void getTypeInfo_positive() {
        technics1.setTechnicsType(TechnicsType.TELEPHONE);
        technics2.setTechnicsType(TechnicsType.TELEPHONE);
        technics3.setTechnicsType(TechnicsType.TELEVISION);
        invoice1.addProduct(technics1);
        invoice1.addProduct(technics2);
        invoice1.addProduct(technics3);
        Map<TechnicsType, Integer> expected = new HashMap<>();
        expected.put(TechnicsType.TELEPHONE, 2);
        expected.put(TechnicsType.TELEVISION, 1);

        Assertions.assertEquals(expected.get(TechnicsType.TELEPHONE),
                target.getTypeInfo(repository.getAll()).get(TechnicsType.TELEPHONE).intValue());
    }

    @Test
    void getTypeInfo_negative() {
        technics1.setTechnicsType(TechnicsType.TELEPHONE);
        technics2.setTechnicsType(TechnicsType.TELEPHONE);
        technics3.setTechnicsType(TechnicsType.TELEVISION);
        invoice1.addProduct(technics1);
        invoice1.addProduct(technics2);
        invoice1.addProduct(technics3);
        final int expected = -5;

        Assertions.assertNotEquals(expected,
                target.getTypeInfo(repository.getAll()).get(TechnicsType.TELEPHONE));
    }

    @Test
    void minInvoice_positive() {
        technics1.setPrice(2000);
        technics2.setPrice(1500);
        technics3.setPrice(1000);
        invoice1.addProduct(technics1);
        invoice2.addProduct(technics2);
        invoice3.addProduct(technics3);
        invoice4.addProduct(technics1);
        invoice5.addProduct(technics2);

        Assertions.assertEquals(1000, target.minInvoice(repository.getAll()));
    }

    @Test
    void minInvoice_negative() {
        technics1.setPrice(2000);
        technics2.setPrice(1500);
        technics3.setPrice(1000);
        invoice1.addProduct(technics1);
        invoice2.addProduct(technics2);
        invoice3.addProduct(technics3);
        invoice4.addProduct(technics1);
        invoice5.addProduct(technics2);

        Assertions.assertNotEquals(0, target.minInvoice(repository.getAll()));
    }

    @Test
    void allInvoiceSum_positive() {
        technics1.setPrice(2000);
        technics2.setPrice(1500);
        technics3.setPrice(1000);
        invoice1.addProduct(technics1);
        invoice2.addProduct(technics2);
        invoice3.addProduct(technics3);

        Assertions.assertEquals(4500, target.allInvoiceSum(repository.getAll()));
    }

    @Test
    void allInvoiceSum_negative() {
        technics1.setPrice(2000);
        technics2.setPrice(1500);
        technics3.setPrice(1000);
        invoice1.addProduct(technics1);
        invoice2.addProduct(technics2);
        invoice3.addProduct(technics3);

        Assertions.assertNotEquals(-400, target.allInvoiceSum(repository.getAll()));
    }

    @Test
    void retailInvoices_positive() {
        invoice1.setInvoiceType(InvoiceType.RETAIL);
        invoice2.setInvoiceType(InvoiceType.RETAIL);
        invoice3.setInvoiceType(InvoiceType.WHOLESALE);
        invoice4.setInvoiceType(InvoiceType.LOW_AGE);
        invoice5.setInvoiceType(InvoiceType.WHOLESALE);

        Assertions.assertEquals(2, target.retailInvoices(repository.getAll()));
    }

    @Test
    void retailInvoices_negative() {
        invoice1.setInvoiceType(InvoiceType.RETAIL);
        invoice2.setInvoiceType(InvoiceType.RETAIL);
        invoice3.setInvoiceType(InvoiceType.WHOLESALE);
        invoice4.setInvoiceType(InvoiceType.LOW_AGE);
        invoice5.setInvoiceType(InvoiceType.WHOLESALE);

        Assertions.assertNotEquals(1, target.retailInvoices(repository.getAll()));
    }

    @Test
    void oneTypeInvoices_positive() {
        technics1.setTechnicsType(TechnicsType.TELEPHONE);
        technics2.setTechnicsType(TechnicsType.TELEVISION);
        technics3.setTechnicsType(TechnicsType.TELEPHONE);
        invoice1.addProduct(technics1);
        invoice1.addProduct(technics3);
        invoice2.addProduct(technics2);
        invoice2.addProduct(technics1);
        invoice3.addProduct(technics3);

        Assertions.assertEquals(2, target.oneTypeInvoices(repository.getAll()).size());
    }

    @Test
    void oneTypeInvoices_negative() {
        technics1.setTechnicsType(TechnicsType.TELEPHONE);
        technics2.setTechnicsType(TechnicsType.TELEVISION);
        technics3.setTechnicsType(TechnicsType.TELEPHONE);
        invoice1.addProduct(technics1);
        invoice1.addProduct(technics3);
        invoice2.addProduct(technics2);
        invoice2.addProduct(technics1);
        invoice3.addProduct(technics3);

        Assertions.assertNotEquals(1, target.oneTypeInvoices(repository.getAll()).size());
    }

    @Test
    void firstThreeInvoices_positive() {
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice2);
        expected.add(invoice3);

        Assertions.assertEquals(expected, target.firstThreeInvoices(repository.getAll()));
    }

    @Test
    void firstThreeInvoices_negative() {
        List<Invoice> expected = new ArrayList<>();
        expected.add(invoice1);
        expected.add(invoice2);
        expected.add(invoice4);
        Assertions.assertNotEquals(expected, target.firstThreeInvoices(repository.getAll()));
    }

    @Test
    void lessThanEighteenInvoice_positive() {
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
    void lessThanEighteenInvoice_negative() {
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