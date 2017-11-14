package app.controller;

/**
 * Created by rail on 11/12/17.
 */
public class Terbilang {

    String[] nomina={"","Satu","Dua","Tiga","Empat","Lima","Enam",
            "Tujuh","Delapan","Sembilan","Sepuluh","Sebelas"};

    public String bilangx(Double angka)
    {
        if(angka<12)
        {
            return nomina[angka.intValue()];
        }

        if(angka>=12 && angka <=19)
        {
            return nomina[angka.intValue()%10] +" Belas ";
        }

        if(angka>=20 && angka <=99)
        {
            return nomina[angka.intValue()/10] +" Puluh "+nomina[angka.intValue()%10];
        }

        if(angka>=100 && angka <=199)
        {
            return "Seratus "+ bilangx(angka%100);
        }

        if(angka>=200 && angka <=999)
        {
            return nomina[angka.intValue()/100]+" Ratus "+bilangx(angka%100);
        }

        if(angka>=1000 && angka <=1999)
        {
            return "Seribu "+ bilangx(angka%1000);
        }

        if(angka >= 2000 && angka <=999999)
        {
            return bilangx(angka/1000)+" Ribu "+ bilangx(angka%1000);
        }

        if(angka >= 1000000 && angka <=999999999)
        {
            return bilangx(angka/1000000)+" Juta "+ bilangx(angka%1000000);
        }

        if(angka >= 1000000000 && angka <=999999999999L)
        {
            return bilangx(angka/1000000000)+" Milyar "+ bilangx(angka%1000000000);
        }

        if(angka >= 1000000000000L && angka <=999999999999999L)
        {
            return bilangx(angka/1000000000000L)+" Triliun "+ bilangx(angka%1000000000000L);
        }

        if(angka >= 1000000000000000L && angka <=999999999999999999L)
        {
            return bilangx(angka/1000000000000000L)+" Quadliun "+ bilangx(angka%1000000000000000L);
        }

        return "";
    }
}
