
public class Row {
    double def_val = 0.0;
    String COD, CNT, LOC, DT;
    double TC = def_val, NC = def_val, NCS = def_val, TD = def_val, ND = def_val, NDS = def_val, RR = def_val, NT = def_val, TT = def_val;
    double SI = def_val, POP = def_val, MA = def_val;

    Row(String COD, String CNT, String LOC, String DT, String TC, String NC,
        String NCS, String TD, String ND, String NDS, String RR,
        String NT, String TT, String SI, String POP, String MA ) {
        this.COD = COD; this.CNT = CNT; this.LOC = LOC; this.DT = DT;
        if (!TC.isEmpty()) this.TC = Double.parseDouble(TC);
        if (!NC.isEmpty()) this.NC = Double.parseDouble(NC);
        if (!NCS.isEmpty()) this.NCS = Double.parseDouble(NCS);
        if (!TD.isEmpty()) this.TD = Double.parseDouble(TD);
        if (!ND.isEmpty()) this.ND = Double.parseDouble(ND);
        if (!NDS.isEmpty()) this.NDS = Double.parseDouble(NDS);
        if (!RR.isEmpty()) this.RR = Double.parseDouble(RR);
        if (!NT.isEmpty()) this.NT = Double.parseDouble(NT);
        if (!TT.isEmpty()) this.TT = Double.parseDouble(TT);
        if (!SI.isEmpty()) this.SI = Double.parseDouble(SI);
        if (!POP.isEmpty()) this.POP = Double.parseDouble(POP);
        if (!MA.isEmpty()) this.MA = Double.parseDouble(MA);
    }

    @Override
    public String toString() {
        return "Row{" +
                "COD='" + COD + '\'' +
                ", CNT='" + CNT + '\'' +
                ", LOC='" + LOC + '\'' +
                ", DT='" + DT + '\'' +
                ", TC=" + TC +
                ", NC=" + NC +
                ", NCS=" + NCS +
                ", TD=" + TD +
                ", ND=" + ND +
                ", NDS=" + NDS +
                ", RR=" + RR +
                ", NT=" + NT +
                ", TT=" + TT +
                ", SI=" + SI +
                ", POP=" + POP +
                ", MA=" + MA +
                '}';
    }
}
