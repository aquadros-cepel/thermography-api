#!/bin/bash

USERNAME="admin"
PASSWORD="admin"
SERVER="34.39.196.181"
#SERVER="localhost"

TOKEN=$(curl -s -X POST http://${SERVER}:8081/api/authenticate \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"$USERNAME\",\"password\":\"$PASSWORD\"}" \
  | grep -oP '"id_token"\s*:\s*"\K[^"]+')

echo -n "$TOKEN"
