#!/usr/bin/env bash

set -u

BASE_URL="https://demo.thermalenergyapp.com"
#BASE_URL="34.95.132.119"
REGISTER_URL="${BASE_URL}/api/register"

LOGIN="andre11"
EMAIL="quadros.andre@gmail.com"
PASSWORD="admin"
LANG_KEY="pt-br"
FIRST_NAME="André Quadros"
LAST_NAME=""
PHONE_NUMBER="+55 24 99246-9052"

TMP_BODY="$(mktemp)"
TMP_HEADERS="$(mktemp)"

cleanup() {
  rm -f "$TMP_BODY" "$TMP_HEADERS"
}
trap cleanup EXIT

JSON_PAYLOAD=$(cat <<EOF
{
  "login": "$LOGIN",
  "firstName": "$FIRST_NAME",
  "lastName": "$LAST_NAME",
  "email": "$EMAIL",
  "password": "$PASSWORD",
  "langKey": "$LANG_KEY",
  "phoneNumber": "$PHONE_NUMBER",
  "activated": true
}
EOF
)

echo "========================================"
echo "Teste do endpoint de cadastro JHipster"
echo "========================================"
echo "URL.............: $REGISTER_URL"
echo "Login...........: $LOGIN"
echo "Email...........: $EMAIL"
echo "Payload enviado:"
echo "$JSON_PAYLOAD"
echo

HTTP_CODE=$(curl -sS -X POST "$REGISTER_URL" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d "$JSON_PAYLOAD" \
  -D "$TMP_HEADERS" \
  -o "$TMP_BODY" \
  -w "%{http_code}")

echo "========================================"
echo "RESULTADO"
echo "========================================"
echo "HTTP Status: $HTTP_CODE"
echo

echo "--- Headers da resposta ---"
cat "$TMP_HEADERS"
echo

echo "--- Body da resposta ---"
cat "$TMP_BODY"
echo
echo "========================================"

if [[ "$HTTP_CODE" == "201" ]]; then
  echo "SUCESSO: endpoint /api/register respondeu 201 Created."
  exit 0
else
  echo "FALHA: endpoint /api/register não respondeu 201."
  exit 1
fi