# Duels (1v1) Plugin

**Duels** je plugin pro Minecraft, který umožňuje hráčům bojovat v soubojích 1v1 s přednastavenými arénami a kity.

🔗 [Stránka pluginu na SpigotMC](https://www.spigotmc.org/resources/duels-1v1-1-16-plugin.95752/)

## 🚀 Funkce
- 🥊 **Souboje 1v1** mezi hráči
- 🏟 **Přednastavené arény** s volitelnými kity
- 🎮 **Snadné ovládání pomocí příkazů**
- ⚙️ **Administrátorské příkazy pro správu arén a kitů**
- 🔄 **Automatický respawn po souboji**

## 📥 Instalace
1. 📂 Stáhněte si nejnovější verzi pluginu ze stránky [SpigotMC](https://www.spigotmc.org/resources/duels-1v1-1-16-plugin.95752/).
2. 📁 Nahrajte soubor `.jar` do složky `plugins` na vašem serveru.
3. 🔄 Restartujte server, aby se plugin načetl.

## 🎮 Použití
Hráči se mohou připojit do souboje pomocí příkazů:
```bash
/duels join <kit>   # Připojí se do nejlepší dostupné arény s vybraným kitem
/duels leave        # Opustí souboj
```

### 🔧 Administrátorské příkazy
```bash
/duels admin             # Zobrazí seznam admin příkazů
/duels admin arenacreate <název>   # Vytvoří novou arénu
/duels admin setspawn              # Nastaví výchozí spawn po souboji
/duels admin createkit <název>     # Uloží inventář jako nový kit
/duels admin setkitname <aréna> <kit>   # Nastaví kit pro danou arénu
/duels admin setmapname <aréna> <název>   # Nastaví název mapy arény
/duels admin setmapauthor <aréna> <autor> # Nastaví autora mapy
/duels admin setlocation1 <aréna>  # Nastaví lokaci prvního hráče
/duels admin setlocation2 <aréna>  # Nastaví lokaci druhého hráče
```

❗ **POZOR:** Po každé změně arény nebo kitu je nutné **restartovat** server, jinak změny nebudou fungovat!

## 🔑 Oprávnění
```yaml
duels.admin   # Oprávnění pro použití admin příkazů
```

## 📌 Plánované funkce
- 📊 **Scoreboard** zobrazující statistiky hráčů
