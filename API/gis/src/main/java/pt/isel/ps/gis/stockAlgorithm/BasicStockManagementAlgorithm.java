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
        float pSeg = (T1 * history[0]) + (T2 * history[7]) + (T3 * history[14]);
        float pTer = (T1 * history[1]) + (T2 * history[8]) + (T3 * history[15]);
        float pQua = (T1 * history[2]) + (T2 * history[9]) + (T3 * history[16]);
        float pQui = (T1 * history[3]) + (T2 * history[10]) + (T3 * history[17]);
        float pSex = (T1 * history[4]) + (T2 * history[11]) + (T3 * history[18]);
        float pSab = (T1 * history[5]) + (T2 * history[12]) + (T3 * history[19]);
        float pDom = (T1 * history[6]) + (T2 * history[13]) + (T3 * history[20]);
        short[] prediction = new short[7];
        prediction[0] = (short) ((T_ANT * pDom) + (T_DIA * pSeg) + (T_SEG * pTer));
        prediction[1] = (short) ((T_ANT * pSeg) + (T_DIA * pTer) + (T_SEG * pQua));
        prediction[2] = (short) ((T_ANT * pTer) + (T_DIA * pQua) + (T_SEG * pQui));
        prediction[3] = (short) ((T_ANT * pQua) + (T_DIA * pQui) + (T_SEG * pSex));
        prediction[4] = (short) ((T_ANT * pQui) + (T_DIA * pSex) + (T_SEG * pSab));
        prediction[5] = (short) ((T_ANT * pSex) + (T_DIA * pSab) + (T_SEG * pDom));
        prediction[6] = (short) ((T_ANT * pSab) + (T_DIA * pDom) + (T_SEG * pSeg));
        return prediction;
    }
}
