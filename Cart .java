package com.mycompany.cyberjocx;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Book> items = new ArrayList<>();

    public void add(Book b) {

        items.add(b);
    }

    public void remove(int id) {

        items.removeIf(
                b -> b.getId() == id
        );
    }

    public double getTotal() {

        double total = 0;

        for (Book b : items) {

            total += b.getPricePoints();
        }

        return total;
    }

    public List<Book> getItems() {

        return items;
    }

    public void clear() {

        items.clear();
    }
}
