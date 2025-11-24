#!/bin/bash

USERNAME="admin"
PASSWORD="admin"

TOKEN=$(curl -s -X POST http://localhost:8080/api/authenticate \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"$USERNAME\",\"password\":\"$PASSWORD\"}" \
  | grep -oP '"id_token"\s*:\s*"\K[^"]+')

echo "✅ Token retrieved successfully:"
echo "$TOKEN"


echo "Token retrieved successfully."
#curl -X POST http://localhost:8080/api/import/plants -H "Authorization: Bearer $TOKEN" -F "file=@src/main/resources/data/plants.csv"
curl -X POST http://localhost:8080/api/import/equipments -H "Authorization: Bearer $TOKEN" -F "file=@src/main/resources/data/equipments.csv"
