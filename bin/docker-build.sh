#!/bin/bash
set -e

echo "======================================"
echo "  JHipster Docker Local Builder"
echo "======================================"

echo ""
echo "👉 Passo 1: Construindo imagem Docker com JIB"
# Original
#./mvnw -Pprod verify jib:dockerBuild

#Sem testes para rodar em desenvolvimento local
./mvnw -Pprod -DskipTests package jib:dockerBuild
