
public class Fraction {
	private int fenZi;
	private int fenMu;
	
	public Fraction() {
	}
	public Fraction(int fenZi, int fenMu) {
		super();
		this.fenZi = fenZi;
		this.fenMu = fenMu;
	}
	public int getFenZi() {
		return fenZi;
	}
	public void setFenZi(int fz) {
		this.fenZi = fz;
	}
	public int getFenMu() {
		return fenMu;
	}
	public void setFenMu(int fm) {
		this.fenMu = fm;
	}
	
	public void huaJian() {
		if (fenZi == 0) {
			fenMu = 1;
		}
		int yueshu = getYueshu();
			if (fenMu > 0) {
				fenZi /= yueshu;
				fenMu /= yueshu;
			} else {
				fenZi /= -yueshu;
				fenMu /= -yueshu;
			}
	}
	
	private int getYueshu() {
		int a = Math.abs(fenZi);
		int b = Math.abs(fenMu);
		int smaller = Math.min(a, b);
		for (int i = smaller; i > 1; i--) {
			if (a % i == 0 && b % i == 0) {
				return i;
			}
		}
		return 1;
	}
	
	public String toString() {
		if (fenMu == 1) {
			return fenZi + "";
		}
		return fenZi + "/" + fenMu;
	}	
	public void jia(Fraction a) {
		this.fenZi = this.fenZi * a.getFenMu() + a.getFenZi() * this.fenMu;
		this.fenMu = this.fenMu * a.getFenMu();
	}
    public void jian(Fraction a) {
		this.fenZi = this.fenZi * a.getFenMu() - a.getFenZi() * this.fenMu;
		this.fenMu = this.fenMu * a.getFenMu();
	} 
}


class MixedFraction extends Fraction{
	private int zhengshu;
	
	public MixedFraction(){}
	public MixedFraction(int zhengshu, int fenzi, int fenmu){
		super(fenzi, fenmu);
		this.zhengshu = zhengshu;
		change();
	}
	
	public void change(){
		if(zhengshu < 0){
			setFenZi(-((-zhengshu) * getFenMu() + getFenZi()));
		}else{
			setFenZi(zhengshu * getFenMu() + getFenZi());
		}
		zhengshu = 0;
	}
	
	@Override
	public String toString() {
		int FZ, FM, ZS;
		FZ = getFenZi();  FM = getFenMu();  ZS = zhengshu;
		ZS = FZ / FM;
		FZ = Math.abs(FZ) - FM * Math.abs(ZS);
		
		if(FZ == 0){
			return ZS + "";
		}
		
		int YUESHU;
		YUESHU = getYueshu(FZ, FM);
		FZ = FZ / YUESHU;
		FM = FM / YUESHU;
		return ZS + "[" + FZ + "/" + FM + "]";	
    }  
	
	public void jia(MixedFraction a) {
		setFenZi(getFenZi() * a.getFenMu() + a.getFenZi() * getFenMu());
	    setFenMu(getFenMu() * a.getFenMu());
	}

	private static int getYueshu(int fenZi, int fenMu) {
	    int a = Math.abs(fenZi);
	    int b = Math.abs(fenMu);
	    int smaller = Math.min(a, b);
	    for (int i = smaller; i > 1; i--) {
	       if (a % i == 0 && b % i == 0) { return i; }
	    }
	    return 1;
    }
	 
	public int getZhengshu() {
		return zhengshu;
	}
	
	public void setZhengshu(int zhengshu) {
		this.zhengshu = zhengshu;
	}
}





