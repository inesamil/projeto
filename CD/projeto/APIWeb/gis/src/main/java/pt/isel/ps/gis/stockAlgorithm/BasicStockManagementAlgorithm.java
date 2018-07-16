package pt.isel.ps.gis.stockAlgorithm;

public class BasicStockManagementAlgorithm implements StockManagementAlgorithm {

    private static final float T1 = 0.1F;
    private static final float T2 = 0.3F;
    private static final float T3 = 0.6F;
    private static final float T_ANT = 0.25F;
    private static final float T_DIA = 0.5F;
    private static final float T_SEG = 0.25F;

    @Override
    public Item[] estimateNextWeek(Item[] history) {
        if (history == null || history.length < 21)
            throw new IllegalArgumentException("The history array must be non null or must have at least 21 positions");
        float pSeg = (T1 * history[0].getQuantity()) + (T2 * history[7].getQuantity()) + (T3 * history[14].getQuantity());
        float pTer = (T1 * history[1].getQuantity()) + (T2 * history[8].getQuantity()) + (T3 * history[15].getQuantity());
        float pQua = (T1 * history[2].getQuantity()) + (T2 * history[9].getQuantity()) + (T3 * history[16].getQuantity());
        float pQui = (T1 * history[3].getQuantity()) + (T2 * history[10].getQuantity()) + (T3 * history[17].getQuantity());
        float pSex = (T1 * history[4].getQuantity()) + (T2 * history[11].getQuantity()) + (T3 * history[18].getQuantity());
        float pSab = (T1 * history[5].getQuantity()) + (T2 * history[12].getQuantity()) + (T3 * history[19].getQuantity());
        float pDom = (T1 * history[6].getQuantity()) + (T2 * history[13].getQuantity()) + (T3 * history[20].getQuantity());
        return new Item[]{
                new Item(null, (short) ((T_ANT * pDom) + (T_DIA * pSeg) + (T_SEG * pTer)), null),
                new Item(null, (short) ((T_ANT * pSeg) + (T_DIA * pTer) + (T_SEG * pQua)), null),
                new Item(null, (short) ((T_ANT * pTer) + (T_DIA * pQua) + (T_SEG * pQui)), null),
                new Item(null, (short) ((T_ANT * pQua) + (T_DIA * pQui) + (T_SEG * pSex)), null),
                new Item(null, (short) ((T_ANT * pQui) + (T_DIA * pSex) + (T_SEG * pSab)), null),
                new Item(null, (short) ((T_ANT * pSex) + (T_DIA * pSab) + (T_SEG * pDom)), null),
                new Item(null, (short) ((T_ANT * pSab) + (T_DIA * pDom) + (T_SEG * pSeg)), null)
        };
    }
}
