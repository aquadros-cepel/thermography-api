#!/bin/bash

TOKEN=$(./bin/login.sh)

echo "🔐 TOKEN: $TOKEN"

curl -X POST http://localhost:8080/api/import/plants \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@src/main/resources/data/plants.csv"

curl -X POST http://localhost:8080/api/import/equipments \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@src/main/resources/data/equipments.csv"

curl -X POST http://localhost:8080/api/import/components \
   -H "Authorization: Bearer $TOKEN" \
   -F "file=@src/main/resources/data/components.csv"
