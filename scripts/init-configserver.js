//init-configserver.js
rs.initiate({
    _id: "rs-config-server", configsvr: true, version: 1,
    members: [
        { _id: 0, host : 'configsvr01:27017' },
        { _id: 1, host : 'configsvr02:27017' },
    ]
})