#!/bin/bash

EXCEL_PATH="$1"
SERVER="localhost"

if [[ -z "$EXCEL_PATH" ]]; then
	echo "Uso: $0 /caminho/para/arquivo.xlsx"
	exit 1
fi

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

curl -X POST http://${SERVER}:8080/api/import/equipments/excel \
	-H "Authorization: Bearer $TOKEN" \
	-F "file=@${EXCEL_PATH}"
