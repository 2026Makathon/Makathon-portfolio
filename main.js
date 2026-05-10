const { app, BrowserWindow } = require('electron')
const path = require('path')

function createWindow() {
    const win = new BrowserWindow({
        width: 618,
        height: 800,
        resizable: false,
        autoHideMenuBar: true,
        useContentSize: true,
        webPreferences: {
            nodeIntegration: false,
            contextIsolation: true
        }
    })
    win.loadFile(path.join(__dirname, 'index.html'))
}

app.whenReady().then(createWindow)