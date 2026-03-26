#!/usr/bin/env bash
# Upload a FLIR thermogram (JPEG) to the API using the token from ./bin/login.sh
# Usage: ./bin/uploadThermogram.sh [SERVER] [FILE]
# Example: ./bin/uploadThermogram.sh 34.39.196.181 src/main/resources/flir/example.jpg

set -euo pipefail

SERVER_DEFAULT="34.39.196.181"
#SERVER_DEFAULT="localhost"
# Use $HOME so tilde expands correctly
FILE_DEFAULT="$HOME/Pictures/IR_3048.jpg"

SERVER="${1:-$SERVER_DEFAULT}"
FILE="${2:-$FILE_DEFAULT}"

# Expand tilde if user passed a path starting with ~
if [[ "$FILE" == ~* ]]; then
  FILE="${FILE/#~/$HOME}"
fi

if [ ! -f "$FILE" ]; then
  echo "Error: file not found: $FILE"
  exit 1
fi

# echo "🔒 Authenticating..."
# TOKEN=$(./bin/login.sh)
# if [ -z "$TOKEN" ]; then
#   echo "Error: empty token returned from login.sh"
#   exit 1
# fi

# echo "🔐 TOKEN: $TOKEN"

echo "📤 Uploading thermogram $FILE to ${SERVER}:5000/py-api/flir/uploadThermogram"

curl -sS -X POST "http://${SERVER}:5000/py-api/flir/uploadThermogram" \
  -F "file=@${FILE}" \
  | python3 -m json.tool

echo
