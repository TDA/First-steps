package arrays;

public class BuySellStockLikeAnIdiot {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            int buyPrice = prices[i];
            for (int j = i + 1; j < prices.length; j++) {
                int sellPrice = prices[j];
                if (buyPrice > sellPrice) {
                    continue;
                }
                int profit = sellPrice - buyPrice;
                maxProfit = Math.max(maxProfit, profit);
            }
        }
        return maxProfit;
    }
    public int maxProfitLinear(int[] prices) {
        int maxProfit = 0;
        int buyPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            int sellPrice = prices[i];
            if (buyPrice > sellPrice) {
                // set Next point to jump to
                buyPrice = sellPrice;
                continue;
            }
            maxProfit = Math.max(maxProfit, sellPrice - buyPrice);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        BuySellStockLikeAnIdiot buySellStockLikeAnIdiot = new BuySellStockLikeAnIdiot();
        int[] prices = {7,1,5,3,6,4};
        int[] prices2 = {7,6,4,3,1};
        System.out.println(buySellStockLikeAnIdiot.maxProfit(prices));;
        System.out.println(buySellStockLikeAnIdiot.maxProfit(prices2));;
        System.out.println(buySellStockLikeAnIdiot.maxProfitLinear(prices));;
        System.out.println(buySellStockLikeAnIdiot.maxProfitLinear(prices2));;
    }
}
