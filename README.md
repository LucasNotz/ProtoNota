# 📦 ProtoNota

**ProtoNota** é uma aplicação em estágio de protótipo projetada para armazenar **notas fiscais** e informações relacionadas de forma segura.

---

## 📌 Visão Geral

O ProtoNota funciona como um sistema de armazenamento local para registros financeiros, incorporando mecanismos básicos de segurança para proteção dos dados do usuário.

---

## ✨ Funcionalidades

- 🔐 **Hash de Senha**  
  As credenciais de login são armazenadas utilizando técnicas de hashing.

- 🔒 **Criptografia de Dados**  
  Informações sensíveis são armazenadas de forma criptografada.

---

## ⚠️ Considerações Importantes

- Este programa foi projetado apenas para sistemas Linux
- Este projeto está em **fase de protótipo**.
- Os mecanismos de segurança **não foram totalmente testados ou auditados**.
- O uso é por conta e risco do usuário — **não é recomendado para produção**.

---

## 🚀 Como Executar

1. Certifique-se de ter o **Java** instalado.
2. Tornar o `.jar` executável
3. Execute o arquivo `.jar`:

   ```bash
   java -jar ProtoNota.jar

---

## 🐛 Problemas Conhecidos (& Exploit)

- Arquivo StateFile.txt deletado
  1. Crie um novo arquivo `StateFile.txt` no mesmo local
  2. Gere um hash SHA-256 da senha que voce deseja
  3. Insira o hash no arquivo e salve
  4. Rode o programa novamente 
