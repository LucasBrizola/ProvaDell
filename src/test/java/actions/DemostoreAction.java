package actions;


import pages.DemostorePage;

public class DemostoreAction {

    private String item;
    private String price;

    private final DemostorePage demostorePage = new DemostorePage();

    public void searchItem(String item) {
        this.item = item;
        demostorePage.searchItem(item);
    }

    public void findItemOnGrid() {
        demostorePage.findItemOnGrid(item);
    }

    public void addItemToCart() {
        demostorePage.addItemToCart();
        this.price = demostorePage.getPrice();
    }

    public void accessChart() {
        demostorePage.accessChart();
    }

    public boolean checkChart() {
       return demostorePage.checkChart(item, item);
    }

    public boolean checkPrice() {
        return demostorePage.checkPrice(price, price);
    }

}
