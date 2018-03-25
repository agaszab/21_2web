package pl.javastart212web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


@Controller
public class PrzychodyController {

    private static final String URL = "jdbc:mysql://localhost:3306/wydatki?characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASS = "kociakolka";
    private Connection connection;

    public PrzychodyController(Connection connection) {
        this.connection = connection;
    }

    @ResponseBody
    @RequestMapping("/")
    public static String main() {

        return "index.html";

    }




    @RequestMapping("/opcja")
    public String wyswietlPrzychody(Model model, @RequestParam Opcje opcje) throws SQLException {
        Przychody przychody = new Przychody();
        PrzychodyDao dao=new PrzychodyDao();

        switch (opcje.getNr()) {
            case 1: { przychody = wczytaj("przychody"); dao.save(przychody); break;}
            case 2: { przychody = wczytaj("wydatki"); dao.save(przychody); break;}
            case 3: { dao.show("przychody"); break; }
            case 4: { dao.show("wydatki"); break; }
        }

        model.addAttribute("przychody", przychody);

        return "przychody";
    }



    public Przychody wczytaj (String rodzaj) throws SQLException{
        Scanner scan=new Scanner(System.in);
        System.out.println("Wpisz pozycję do bazy przychodów/wydatków");
        System.out.println("Opis transakcji:");
        String opis=scan.nextLine();
        System.out.println("Kwota:");
        long kwota=scan.nextLong();
        LocalDate data=LocalDate.now();
        Przychody przychody=new Przychody();
        przychody.setType(rodzaj);
        przychody.setDescription(opis);
        przychody.setAmount(kwota);
        przychody.setDate(data);

        return przychody;
    }


}