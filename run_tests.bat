:: Gerado por IA
@echo off
setlocal enabledelayedexpansion

:: Define os tamanhos e os tipos de entrada
set TAMANHOS=100 1000 10000 100000 1000000
set TIPOS=RANDOM REPEATED CRESCENT DECRESCENT

set PROGRAMA=./build/libs/ACD-TP1.jar

echo Iniciando a bateria de testes do analisador de algoritmos...

:: Loop externo para iterar sobre todos os tamanhos
for %%T in (%TAMANHOS%) do (
    
    :: Cria o diretorio para o tamanho atual, caso nao exista
    set DIR_RESULTADOS=.\results\%%T
    if not exist "!DIR_RESULTADOS!" mkdir "!DIR_RESULTADOS!"
    
    echo ---------------------------------------------------
    echo Testando para o tamanho: %%T
    echo ---------------------------------------------------
    
    :: Loop interno para iterar sobre os tipos de entrada
    for %%I in (%TIPOS%) do (
        echo  Executando entrada: %%I
        
        :: Executa o programa Java
        java -jar "%PROGRAMA%" --inputType %%I --size %%T --samples 30 --resultsFile "!DIR_RESULTADOS!\%%I.csv"
            
        :: Verifica se o comando anterior rodou sem erros
        if !ERRORLEVEL! EQU 0 (
            echo  [OK] Salvo em: !DIR_RESULTADOS!\%%I.csv
        ) else (
            echo  [ERRO] Falha ao rodar tamanho %%T com entrada %%I
        )
    )
)

echo ---------------------------------------------------
echo Todos os testes foram finalizados!
pause