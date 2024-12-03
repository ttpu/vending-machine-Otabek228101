import java.util.*;

public class VendingMachine {
    private Map<String, Double> beverages;
    private Map<Integer, Double> cards;
    private Map<Integer, Column> columns;
    public VendingMachine() {
        beverages = new HashMap<>();
        cards = new HashMap<>();
        columns = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            columns.put(i, new Column());
        }
    }
    public void addBeverage(String name, double price) {
        beverages.put(name, price);
    }
    public double getPrice(String beverageName) {
        return beverages.getOrDefault(beverageName, -1.0);
    }
    public void rechargeCard(int cardId, double credit) {
        cards.put(cardId, cards.getOrDefault(cardId, 0.0) + credit);
    }
    public double getCredit(int cardId) {
        return cards.getOrDefault(cardId, -1.0);
    }
    public void refillColumn(int column, String beverageName, int cans) {
        if (!columns.containsKey(column) || !beverages.containsKey(beverageName)) {
            return;
        }
        Column col = columns.get(column);
        col.beverageName = beverageName;
        col.cans = cans;
    }
    public int availableCans(String beverageName) {
        if (!beverages.containsKey(beverageName)) {
            return 0;
        }
        int totalCans = 0;
        for (Column col : columns.values()) {
            if (beverageName.equals(col.beverageName)) {
                totalCans += col.cans;
            }
        }
        return totalCans;
    }
    public int sell(String beverageName, int cardId) {
        if (!beverages.containsKey(beverageName) || !cards.containsKey(cardId)) {
            return -1;
        }
        double price = beverages.get(beverageName);
        double credit = cards.get(cardId);
        if (credit < price) {
            return -1;
        }
        for (Map.Entry<Integer, Column> entry : columns.entrySet()) {
            int columnNumber = entry.getKey();
            Column col = entry.getValue();
            if (beverageName.equals(col.beverageName) && col.cans > 0) {
                col.cans--;
                cards.put(cardId, credit - price);
                return columnNumber;
            }
        }
        return -1;
    }
    private static class Column {
        String beverageName;
        int cans;
        Column() {
            beverageName = null;
            cans = 0;
        }
    }
}
