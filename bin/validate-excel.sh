#!/bin/bash

EXCEL_PATH="src/main/resources/data/Equipments4_Altamira.xlsx"
#SERVER="localhost"
SERVER="34.95.132.119"

if [[ ! -f "$EXCEL_PATH" ]]; then
	echo "Arquivo não encontrado: $EXCEL_PATH"
	exit 1
fi

TOKEN=$(./bin/login.sh)

if [[ -z "$TOKEN" ]]; then
	echo "Falha ao obter token."
	exit 1
fi

echo "🔐 TOKEN: $TOKEN"

curl -X POST http://${SERVER}:8081/api/import/equipments/excel/validate \
	-H "Authorization: Bearer $TOKEN" \
	-F "file=@${EXCEL_PATH}"
