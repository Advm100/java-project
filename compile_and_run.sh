#!/bin/bash
mkdir -p bin
rm -rf bin/*
find src -name "*.java" > sources.txt
javac -d bin @sources.txt

if [ $? -eq 0 ]; then
  echo "Compilation réussie, lancement de l'application..."
  java -cp bin com.minigames.app.MiniGamesApp
else
  echo "Échec de la compilation."
fi