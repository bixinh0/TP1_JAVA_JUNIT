package br.edu.infnet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScientificCalculatorTest {

    // Tolerância usada nas comparações com double
    private static final double DELTA = 0.000001;

    // Test fixture: objeto compartilhado por todos os métodos de teste
    private ScientificCalculator calc;


    // 1. FIXTURE - preparação e limpeza do ambiente

    // FASE 1 - SETUP: executado antes de cada teste
    @BeforeEach
    void setUp() {
        calc = new ScientificCalculator();
        System.out.println("setUp() executado - instância criada: "
                + System.identityHashCode(calc));
    }

    // FASE 4 - TEARDOWN: executado depois de cada teste
    @AfterEach
    void tearDown() {
        calc = null;
    }


    // 2. OPERAÇÕES BÁSICAS - add, subtract, multiply, divide

    @Test
    void add_twoPositiveNumbers_returnsSum() {
        logExecucao("add_twoPositiveNumbers_returnsSum");
        assertEquals(15.0, calc.add(10, 5));
    }

    @Test
    void subtract_smallerFromLarger_returnsDifference() {
        logExecucao("subtract_smallerFromLarger_returnsDifference");

        // FASE 1 - SETUP (complemento): define os valores de entrada
        double a = 20;
        double b = 8;

        // FASE 2 - EXECUTION: chama o método que está sendo testado
        double resultado = calc.subtract(a, b);

        // FASE 3 - ASSERTION: verifica se o resultado é o esperado
        assertEquals(12.0, resultado);
    }

    @Test
    void multiply_integerByDecimal_returnsProduct() {
        logExecucao("multiply_integerByDecimal_returnsProduct");
        assertEquals(10.0, calc.multiply(4, 2.5));
    }

    @Test
    void divide_exactDivision_returnsQuotient() {
        logExecucao("divide_exactDivision_returnsQuotient");
        assertEquals(3.0, calc.divide(9, 3));
    }


    // 3. FUNÇÕES AVANÇADAS - squareRoot, log

    @Test
    void squareRoot_positiveNumber_returnsRoot() {
        logExecucao("squareRoot_positiveNumber_returnsRoot");
        assertAproximado(5.0, calc.squareRoot(25));
    }

    @ParameterizedTest(name = "log({0}) = {1}")
    @CsvSource({
            "1,                 0.0",
            "2.718281828459045, 1.0",
            "10,                2.302585",
            "100,               4.605170"
    })
    void log_positiveValues_returnsNaturalLogarithm(double entrada, double esperado) {
        logExecucao("log_positiveValues_returnsNaturalLogarithm com entrada " + entrada);
        assertAproximado(esperado, calc.log(entrada));
    }


    // 4. FUNÇÕES TRIGONOMÉTRICAS - sin (ângulo em graus)

    @ParameterizedTest(name = "sin({0}°) = {1}")
    @CsvSource({
            "0,   0.0",
            "30,  0.5",
            "90,  1.0",
            "180, 0.0"
    })
    void sin_notableAnglesInDegrees_returnsExpectedValue(double graus, double esperado) {
        logExecucao("sin_notableAnglesInDegrees_returnsExpectedValue com " + graus + " graus");
        assertAproximado(esperado, calc.sin(graus));
    }


    // 5. CENÁRIOS DE ERRO - entradas inválidas que lançam exceção

    @Test
    void squareRoot_negativeNumber_throwsIllegalArgumentException() {
        logExecucao("squareRoot_negativeNumber_throwsIllegalArgumentException");
        assertLancaIllegalArgument(() -> calc.squareRoot(-9), "Negative number");
    }

    @Test
    void divide_byZero_throwsIllegalArgumentException() {
        logExecucao("divide_byZero_throwsIllegalArgumentException");
        assertLancaIllegalArgument(() -> calc.divide(10, 0), "Division by zero");
    }


    // 6. MÉTODOS AUXILIARES - evitam repetição nos testes

    // Compara valores double usando a tolerância padrão da classe
    private void assertAproximado(double esperado, double obtido) {
        assertEquals(esperado, obtido, DELTA);
    }

    // Verifica se o código lança IllegalArgumentException com a mensagem esperada
    private void assertLancaIllegalArgument(Executable codigo, String mensagemEsperada) {
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, codigo);
        assertEquals(mensagemEsperada, excecao.getMessage());
    }

    // Exibe no console qual teste está sendo executado
    private void logExecucao(String nomeDoTeste) {
        System.out.println("Executando " + nomeDoTeste);
    }
}