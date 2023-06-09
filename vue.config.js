// vue.config.js
module.exports = {
    /*     proxy all webpack dev-server requests starting with /api
         to our Spring Boot backend (localhost:8088) using http-proxy-middleware
         see https://cli.vuejs.org/config/#devserver-proxy*/
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8088', // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
                ws: true,
                changeOrigin: true
            }
        },
    },

    pages: {
        index: {
            entry: 'src/main/vue/main.js',
            template: 'src/main/vue/index.html',
        },
    },

    /*     Change build paths to make them Maven compatible
         see https://cli.vuejs.org/config/*/
    outputDir: 'target/classes/public',

    assetsDir: 'static',

    pluginOptions: {
      i18n: {
        locale: 'de',
        fallbackLocale: 'en',
        localeDir: 'locales',
        enableInSFC: false
      }
    }
};
