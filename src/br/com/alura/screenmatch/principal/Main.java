package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.api.BuscaService;
import br.com.alura.screenmatch.calculos.FiltroRecomendacao;
import br.com.alura.screenmatch.modelos.*;
import br.com.alura.screenmatch.calculos.CalculadoraDeTempo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.net.http.HttpClient.newHttpClient;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        List<Titulo> playlist = new ArrayList<>();

        // Loop principal para interagir com o usuário e buscar títulos
        while (true) {
            System.out.print("Buscar por titulo (ou 'sair' para sair): ");
            String busca = input.nextLine();

            // Verifica se o usuário deseja sair
            if (busca.equalsIgnoreCase("sair")) {
                break;
            }

            // Cria uma instância de BuscaService para buscar informações sobre o título
            BuscaService buscaService = new BuscaService();
            TituloOmdb meuTituloOmdb = buscaService.buscarTitulo(busca);

            // Verifica se o título foi encontrado
            if (meuTituloOmdb != null) {

                try {
                    // Cria uma instância de Titulo usando as informações obtidas
                    Titulo meuTitulo = new Titulo(meuTituloOmdb);
                    System.out.println("Classe: Titulo usando o toString\n");
                    System.out.println(meuTitulo);
                    System.out.println("Escopo Existente\n");

                    // Adiciona o título à playlist
                    playlist.add(meuTitulo);

                    // Salva a playlist em um arquivo.txt
                    BuscaService.salvarPlaylist(playlist);

                } catch (NumberFormatException e) {
                    System.out.print("Aconteceu um erro: ");
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println("Erro ao salvar a playlist: " + e.getMessage());
                }
            } else {
                System.out.println("Título não encontrado.");
            }
        }

        // Exibe a playlist final e encerra o programa
        System.out.println(playlist);
        System.out.println("\nPrograma encerrado.");

    }
}
