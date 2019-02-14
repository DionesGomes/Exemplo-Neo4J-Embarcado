package com.ifpb.neo4j.visao;


import com.ifpb.neo4j.modelo.Pessoa;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.time.LocalDate;


public class App {

    public static void main(String[] args) {

        GraphDatabaseService graph =  new GraphDatabaseFactory()
                .newEmbeddedDatabase(new File("graph.db"));

//        Pessoa pessoa1 = new Pessoa("111.111.111-01", "João",
//                LocalDate.of(1992,02,01));
//        Pessoa pessoa2 = new Pessoa("222.222.222-02", "Maria",
//                LocalDate.of(1990,12,30));
//
//        exemploSalvarPessoa(graph, pessoa1);
//        exemploSalvarPessoa(graph, pessoa2);

        exemploListarPessoas(graph);

        graph.shutdown();

    }

    private static void exemploListarPessoas(GraphDatabaseService graph) {
        try(Transaction tx = graph.beginTx()){
            ResourceIterator<Node> nos = graph.findNodes(Label.label("Pessoa"));
            nos.stream().forEach(n-> System.out.println(n.getAllProperties()));
            tx.success();
        }
    }

    private static void exemploSalvarPessoa(GraphDatabaseService graph, Pessoa p) {
        try(Transaction tx = graph.beginTx()){
            Node pessoa = graph.createNode(Label.label("Pessoa"));

            pessoa.setProperty("cpf", p.getCpf());
            pessoa.setProperty("nome", p.getNome());
            pessoa.setProperty("nascimento", p.getNascimento());

            tx.success();
        }
    }

}