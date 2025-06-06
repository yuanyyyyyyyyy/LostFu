export default {
    data: {
        lineData: [
            {
                strokeColor: '#0081ff',
                fillColor: '#cce5ff',
                data: [763, 550, 551, 554, 731, 654, 525, 696, 595, 628, 791, 505, 613, 575, 475, 553, 491, 680, 657, 716],
                gradient: true,
            }
        ],
        // 线形图模拟数据
        lineOps: {
            xAxis: {
                min: 0,
                max: 20,
                display: false,
            },
            yAxis: {
                min: 0,
                max: 1000,
                display: false,
            },
            series: {
                lineStyle: {
                    width: "1px",
                    smooth: true,
                },
                topPoint:{
                    shape:"circle",
                    fillColor:"#F9A11C"
                },
                headPoint: {
                    shape: "circle",
                    size: 20,
                    strokeWidth: 1,
                    fillColor: '#ffffff',
                    strokeColor: '#007aff',
                    display: true,
                },
                loop: {
                    margin: 2,
                }
            }
        },
        // 柱状图
        barData: [
            {
                fillColor: '#B2DCD6',
                data: [763, 550, 551, 554, 731, 654, 525, 696, 595, 628],
            },
            {
                fillColor: '#F9A11C',
                data: [535, 776, 615, 444, 694, 785, 677, 609, 562, 410],
            },
            {
                fillColor: '#1A89FF',
                data: [673, 500, 574, 483, 702, 583, 437, 506, 693, 657],
            },
        ],
        barOps: {
            xAxis: {
                min: 0,
                max: 10,
                display: true,
                axisTick: 5,
            },
            yAxis: {
                min: 0,
                max: 1000,
                display: true,
            },

        },

    },

}