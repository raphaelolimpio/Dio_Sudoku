package br.com.dio;

import br.com.dio.model.Board;
import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.Scanner;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

public class Main {
    // comando com os argumentos;
    // java -cp bin br.com.dio.Main "0,0;4,false" "0,1;7,false" "0,2;9,true"
    // "0,3;5,false" "0,4;8,true" "0,5;6,true" "0,6;2,true" "0,7;3,false"
    // "0,8;1,false" "1,0;1,false" "1,1;3,true" "1,2;5,false" "1,3;4,false"
    // "1,4;7,true" "1,5;2,false" "1,6;8,false" "1,7;9,true" "1,8;6,true"
    // "2,0;2,false" "2,1;6,true" "2,2;8,false" "2,3;9,false" "2,4;1,true"
    // "2,5;3,false" "2,6;7,false" "2,7;4,false" "2,8;5,true" "3,0;5,true"
    // "3,1;1,false" "3,2;3,true" "3,3;7,false" "3,4;6,false" "3,5;4,false"
    // "3,6;9,false" "3,7;8,true" "3,8;2,false" "4,0;8,false" "4,1;9,true"
    // "4,2;7,false" "4,3;1,true" "4,4;2,true" "4,5;5,true" "4,6;3,false"
    // "4,7;6,true" "4,8;4,false" "5,0;6,false" "5,1;4,true" "5,2;2,false"
    // "5,3;3,false" "5,4;9,false" "5,5;8,false" "5,6;1,true" "5,7;5,false"
    // "5,8;7,true" "6,0;7,true" "6,1;5,false" "6,2;4,false" "6,3;2,false"
    // "6,4;3,true" "6,5;9,false" "6,6;6,false" "6,7;1,true" "6,8;8,false"
    // "7,0;9,true" "7,1;8,true" "7,2;1,false" "7,3;6,false" "7,4;4,true"
    // "7,5;7,false" "7,6;5,false" "7,7;2,true" "7,8;3,false" "8,0;3,false"
    // "8,1;2,false" "8,2;6,true" "8,3;8,true" "8,4;5,true" "8,5;1,false"
    // "8,6;4,true" "8,7;7,false" "8,8;9,false"

    private final static Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        System.out.println("Argumentos recebidos:");
        for (String arg : args) {
            System.out.println(arg);
        }

        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]));

        validarArgumentos(positions);

        var option = -1;

        while (true) {
            System.out.println("Selecione uma das opções a seguir:");
            System.out.println("1 - Iniciar um novo jogo");
            System.out.println("2 - Colocar um novo numero");
            System.out.println("3 - Remover um numero");
            System.out.println("4 - visualizar jogo atual");
            System.out.println("5 - verificar Status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8- sair");

            option = scanner.nextInt();

            switch (option) {
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishiGame();
                case 8 -> System.exit(0);
                default -> System.out.println("opção invalida, selecione aopção valida");

            }
        }

    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("jogo ja foi iniciado");
            return;
        }

        System.out.println("o Jogo esta Pronto para Começar!");
    }

    private static void finishiGame() {
        if (isNull(board)) {
            System.out.println("jogo nao foi iniciado");
            return;
        }

        if (board.gameIsFinished()) {
            System.out.println("parabens, você concluiu o jogo");
            showCurrentGame();
            board = null;
        } else if (board.hasError()) {
            System.out.println("seu jogo contem erros, verifique seu board e ajute-o");

        } else {
            System.out.println("voce ainda precisa preencher algum espaço");
        }
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println("jogo nao foi iniciado");
            return;
        }

        System.out.println("tem certeza que deseja limpar o jogo e perder todo o seu progresso");
        var confira = scanner.next();
        while (!confira.equalsIgnoreCase("sim") && !confira.equalsIgnoreCase("nao")) {
            System.out.println("informe sim ou nao");
            confira = scanner.next();

        }

        if (confira.equalsIgnoreCase("sim")) {
            board.reset();
        }

    }

    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println("jogo nao foi iniciado");
            return;
        }
        System.out.printf("o jogo atualmente se encontra no status %s\n", board.getStatus().getLabel());
        if (board.hasError()) {
            System.out.println("o jogo contem erros");
        } else {
            System.out.println("o jogo nao contem erros");
        }

    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("jogo nao foi iniciado");
            return;
        }
        var args = new Object[81];
        var argPos = 0;

        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col : board.getSpaces()) {
                args[argPos++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        System.out.println("seu jogo se encontra da seguinte forma:");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);

    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("jogo nao foi iniciado");
            return;
        }
        System.out.println("informe a coluna em que o numero sera inserido");
        var col = runUntiGetValidNumber(0, 8);
        System.out.println("informe a linha em que o numero sera inserido");
        var row = runUntiGetValidNumber(0, 8);
        if (!board.clearValue(col, row)) {
            System.out.printf("posicao [%s, %s] ja foi preenchida\n", col, row);

        }
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println("jogo nao foi iniciado");
            return;
        }
        System.out.println("informe a coluna em que o numero sera inserido");
        var col = runUntiGetValidNumber(0, 8);
        System.out.println("informe a linha em que o numero sera inserido");
        var row = runUntiGetValidNumber(0, 8);
        System.out.printf("informe o numero que deseja inserir em [%s, %s]\n", col, row);
        var value = runUntiGetValidNumber(1, 9);
        if (!board.changeValues(col, row, value)) {
            System.out.printf("posicao [%s, %s] ja foi preenchida\n", col, row);

        }

    }

    private static int runUntiGetValidNumber(final int min, final int max) {
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("informe um numero entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }

    private static void validarArgumentos(Map<String, String> positions) {
        List<String> faltando = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (int j = 0; j < BOARD_LIMIT; j++) {
                String key = i + "," + j;
                if (!positions.containsKey(key)) {
                    faltando.add(key);
                }
            }
        }
        if (!faltando.isEmpty()) {
            System.out.println("Faltam argumentos para as posições:");
            faltando.forEach(System.out::println);
        } else {
            System.out.println("Todos os argumentos necessários estão presentes!");
        }
    }

}
