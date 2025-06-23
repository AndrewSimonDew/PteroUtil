[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

# PteroUtil 🚀

A lightweight Minecraft Velocity plugin to control Pterodactyl-managed servers directly from your Minecraft chat.

---

## Features ✨

- Start, stop, restart, and check the status of Pterodactyl servers  
- Simple and intuitive command: `/serverctl <start|stop|restart|status> <servername>`  
- Permission-based access control with `pteroutil.serverctl`  
- Easy integration with your existing Pterodactyl setup  

---

## Installation ⚙️

1. Download the latest `.jar` file from the [Releases](https://github.com/AndrewSimonDew/PteroUtil/releases) page  
2. Place the `.jar` file into your Velocity proxy’s `plugins` folder  
3. Restart your Velocity proxy server  
4. Configure the plugin by editing the `config.yml` (add your Pterodactyl API key and endpoint)  

---

## Configuration 🛠️

Create or edit the `config.yml` in the plugin folder with your Pterodactyl API info and servers:

```yaml
apikey: YOUR API KEY HERE
url: https://YOUR PANEL URL HERE

servers:
  survival: PTERODACTYL SERVER UUID HERE
  creative: PTERODACTYL SERVER UUID HERE

```

---


### Command breakdown:

| Command  | Description                            | Example                      |
| -------- | ----------------------------------   | ----------------------------|
| start    | Starts the specified Pterodactyl server | `/serverctl start survival`  |
| stop     | Stops the specified Pterodactyl server  | `/serverctl stop creative`   |
| restart  | Restarts the specified server            | `/serverctl restart survival`|
| status   | Checks the current status of the server  | `/serverctl status creative` |

### Notes:

- Replace `<servername>` with the server name you set in your `config.yml` under `servers:`  
- You **must** have the `pteroutil.serverctl` permission to run these commands  
- Server UUIDs are linked in the config, so just use the friendly name, not UUID


---

## Contact 🙌

- Need help or wanna chat? Hit me up on Discord: andrexdev

---

## License 📄

This project is licensed under the MIT License - see the [LICENSE](https://github.com/AndrewSimonDew/PteroUtil?tab=MIT-1-ov-file) file for details.