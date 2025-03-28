@echo off
javac -d bin src/com/minigames/user/*.java
javac -d bin -cp bin src/com/minigames/app/*.java
javac -d bin -cp bin src/com/minigames/ui/*.java
javac -d bin -cp bin src/com/minigames/games/*.java