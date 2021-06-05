package sample.model;

public class Produk {
    private String kodeProduk;
    private String kodeKedai;
    private String namaProduk;
    private int hargaProduk;
    private String kategoriProduk;

    public Produk(String kodeProduk, String kodeKedai, String namaProduk, int hargaProduk, String kategoriProduk) {
        this.kodeProduk = kodeProduk;
        this.kodeKedai = kodeKedai;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.kategoriProduk = kategoriProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeKedai(String kodeKedai) {
        this.kodeKedai = kodeKedai;
    }

    public String getKodeKedai() {
        return kodeKedai;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setHargaProduk(int hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public int getHargaProduk() {
        return hargaProduk;
    }

    public void setKategoriProduk(String kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }

    public String getKategoriProduk() {
        return kategoriProduk;
    }
}
