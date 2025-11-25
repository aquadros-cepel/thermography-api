#!/bin/bash

USERNAME="admin"
PASSWORD="admin"
SERVER="35.247.197.28"
TOKEN=$(curl -s -X POST http://${SERVER}:8080/api/authenticate \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"$USERNAME\",\"password\":\"$PASSWORD\"}" \
  | grep -oP '"id_token"\s*:\s*"\K[^"]+')

echo -n "$TOKEN"
