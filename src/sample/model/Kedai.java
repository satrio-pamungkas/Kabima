package sample.model;

public class Kedai {
    private String kodeKedai;
    private String namaKedai;
    private String kategoriKedai;
    private String deskripsiKedai;

    public Kedai(String kodeKedai, String namaKedai, String kategoriKedai, String deskripsiKedai) {
        this.kodeKedai = kodeKedai;
        this.namaKedai = namaKedai;
        this.kategoriKedai = kategoriKedai;
        this.deskripsiKedai = deskripsiKedai;
    }

    public void setKodeKedai() {
        this.kodeKedai = kodeKedai;
    }

    public String getKodeKedai() {
        return kodeKedai;
    }

    public void setNamaKedai() {
        this.namaKedai = namaKedai;
    }

    public String getNamaKedai() {
        return namaKedai;
    }

    public void setKategoriKedai() {
        this.kategoriKedai = kategoriKedai;
    }

    public String getKategoriKedai() {
        return kategoriKedai;
    }

    public void setDeskripsiKedai() {
        this.deskripsiKedai = deskripsiKedai;
    }

    public String getDeskripsiKedai() {
        return deskripsiKedai;
    }
}
