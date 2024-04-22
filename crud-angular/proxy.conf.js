/*
const PROXY_CONFIG = [
  {
    context: ['/api'], -> configurado na API Spring Controller
    target: 'http://localhost:8080', -> URL e porta padrão da aplicação Spring
    secure: false, -> Sem SSL configurada
    loglevel: 'debug' -> Logs aparecem somente em Debug
  }
]

- alterar o package.json -> "start": "ng serve --proxy-config proxy.conf.js",
*/
const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'http://localhost:8080',
    secure: false,
    loglevel: 'debug'
  }
]

module.exports = PROXY_CONFIG
