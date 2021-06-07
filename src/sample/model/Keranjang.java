package sample.model;

public class Keranjang {
    private String kodeKeranjang;
    private String kodeProduk;
    private String kodeKedai;
    private String namaProduk;
    private int hargaProduk;
    private int kuantitasProduk;
    private int totalHarga;

    public Keranjang(String kodeKeranjang, String kodeProduk, String kodeKedai, String namaProduk, int hargaProduk,
                     int kuantitasProduk, int totalHarga) {
        this.kodeKeranjang = kodeKeranjang;
        this.kodeProduk = kodeProduk;
        this.kodeKedai = kodeKedai;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.kuantitasProduk = kuantitasProduk;
        this.totalHarga = totalHarga;
    }

    public String getKodeKeranjang() {
        return kodeKeranjang;
    }

    public void setKodeKeranjang(String kodeKeranjang) {
        this.kodeKeranjang = kodeKeranjang;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getKodeKedai() {
        return kodeKedai;
    }

    public void setKodeKedai(String kodeKedai) {
        this.kodeKedai = kodeKedai;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(int hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public int getKuantitasProduk() {
        return kuantitasProduk;
    }

    public void setKuantitasProduk(int kuantitasProduk) {
        this.kuantitasProduk = kuantitasProduk;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }
}
