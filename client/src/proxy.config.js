const PROXY_CONFIG = [
	{
	  context: ['/ws'],
	  target: 'ws://localhost:8082',
	  ws: true,
	  secure: false,
	  changeOrigin: true,
	  pathRewrite: { '^/ws': '' },
	  logLevel: 'debug'
	},
	{
	  context: ['/api'],
	  target: 'http://localhost:8082',
	  secure: false,
	  changeOrigin: true,
	  logLevel: 'debug'
	}
  ];
  
  module.exports = PROXY_CONFIG;