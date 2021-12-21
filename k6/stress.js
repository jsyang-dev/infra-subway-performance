import http from 'k6/http';
import {check, sleep} from 'k6';

export let options = {
    stages: [
        {duration: '5s', target: 50},
        {duration: '10s', target: 100},
        {duration: '5s', target: 100},
        {duration: '10s', target: 200},
        {duration: '5s', target: 200},
        {duration: '10s', target: 300},
        {duration: '5s', target: 300},
        {duration: '10s', target: 400},
        {duration: '5s', target: 400},
        {duration: '15s', target: 0},
    ],
    thresholds: {
        http_req_duration: ['p(99)<100'],
    },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
    let pathResponse = http.get(`${BASE_URL}/paths/?source=1&target=6`);
    check(pathResponse, {
        'found path': response => response.status === 200
    });

    sleep(1);
};
