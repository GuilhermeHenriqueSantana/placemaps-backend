package com.esoft.placemaps.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DocumentoHelperTeste {

  @Test
  void testeDocumentoValido() {
    String cnpj = "20255771000137";
    String cpf = "47042936027";
    Boolean cnpjValido = DocumentoHelper.documentoValido(cnpj);
    Boolean cpfValido = DocumentoHelper.documentoValido(cpf);
    Assertions.assertTrue((cnpjValido && cpfValido));
  }

  @Test
  void testarCnpjValido() {
    String cnpj = "20255771000137";
    Boolean valido = DocumentoHelper.cnpjValido(cnpj);
    Assertions.assertTrue(valido);
  }

  @Test
  void testarCnpjInvalido() {
    String cnpj = "12345678910123";
    Boolean valido = DocumentoHelper.cnpjValido(cnpj);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCnpjInvalidoNulo() {
    String cnpj = null;
    Boolean valido = DocumentoHelper.cnpjValido(cnpj);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCnpjInvalidoVazio() {
    String cnpj = "";
    Boolean valido = DocumentoHelper.cnpjValido(cnpj);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCnpjInvalidoComLetras() {
    String cnpj = "5TESTE60000138";
    Boolean valido = DocumentoHelper.cnpjValido(cnpj);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCnpjInvalidoGrande() {
    String cnpj = "202557710001377";
    Boolean valido = DocumentoHelper.cnpjValido(cnpj);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCnpjInvalidoPequeno() {
    String cnpj = "2025577100013";
    Boolean valido = DocumentoHelper.cnpjValido(cnpj);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCpfValido() {
    String cpf = "47042936027";
    Boolean valido = DocumentoHelper.cpfValido(cpf);
    Assertions.assertTrue(valido);
  }

  @Test
  void testarCpfInvalido() {
    String cpf = "12345678910";
    Boolean valido = DocumentoHelper.cpfValido(cpf);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCpfInvalidoNulo() {
    String cpf = null;
    Boolean valido = DocumentoHelper.cpfValido(cpf);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCpfInvalidoVazio() {
    String cpf = "";
    Boolean valido = DocumentoHelper.cpfValido(cpf);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCpfInvalidoComLetras() {
    String cpf = "47042TESTE7";
    Boolean valido = DocumentoHelper.cpfValido(cpf);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCpfInvalidoGrande() {
    String cpf = "470429360271";
    Boolean valido = DocumentoHelper.cpfValido(cpf);
    Assertions.assertFalse(valido);
  }

  @Test
  void testarCpfInvalidoPequeno() {
    String cpf = "4704293602";
    Boolean valido = DocumentoHelper.cpfValido(cpf);
    Assertions.assertFalse(valido);
  }

}
