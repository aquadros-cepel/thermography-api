#!/bin/bash

TOKEN=$(./bin/login.sh)

echo "🔐 TOKEN: $TOKEN"

SERVER="34.39.196.181"

# curl -X POST http://${SERVER}:8081/api/import/plants \
#   -H "Authorization: Bearer $TOKEN" \
#   -F "file=@src/main/resources/data/plants.csv"

# curl -X POST http://${SERVER}:8081/api/import/equipments \
#   -H "Authorization: Bearer $TOKEN" \
#   -F "file=@src/main/resources/data/equipments.csv"

curl -X POST "http://${SERVER}:8081/api/import/components" \
   -H "Authorization: Bearer $TOKEN" \
   -F "file=@src/main/resources/data/components.csv"

curl -X POST "http://${SERVER}:8081/api/import/risks" \
   -H "Authorization: Bearer $TOKEN" \
   -F "file=@src/main/resources/data/risks.csv"
