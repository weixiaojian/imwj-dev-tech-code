"use client"

import React, {useEffect, useRef, useState} from 'react'
// @ts-ignore
import {LuckyGrid} from '@lucky-canvas/react'
import {queryRaffleAwardList, randomRaffle} from "@/apis";
import {RaffleAwardVO} from "@/types/RaffleAwardVO";

/**
 * 大转盘文档：https://100px.net/docs/grid.html
 * @constructor
 */
export function LuckyGridPage() {
    // 参数配置
    let strategyId = 100006;
    if (typeof window !== 'undefined') {
        const queryParams = new URLSearchParams(window.location.search);
        const strategyIdParam = queryParams.get('strategyId');
        strategyId = strategyIdParam ? Number(strategyIdParam) : 100006;
    }
    // 背景
    const [blocks] = useState([
        {padding: '10px', background: '#869cfa'}
    ])
    // 奖品列表存储
    const [prizes, setPrizes] = useState([{}])
    /*const [prizes] = useState([
        {x: 0, y: 0, fonts: [{text: 'A', top: '35%'}]},
        {x: 1, y: 0, fonts: [{text: 'B', top: '35%'}]},
        {x: 2, y: 0, fonts: [{text: 'C', top: '35%'}]},
        {x: 2, y: 1, fonts: [{text: 'D', top: '35%'}]},
        {x: 2, y: 2, fonts: [{text: 'E', top: '35%'}]},
        {x: 1, y: 2, fonts: [{text: 'F', top: '35%'}]},
        {x: 0, y: 2, fonts: [{text: 'G', top: '35%'}]},
        {x: 0, y: 1, fonts: [{text: 'H', top: '35%'}]},
    ])*/
    // 操作按钮
    const [buttons] = useState([
        {x: 1, y: 1, background: "#7f95d1", fonts: [{text: '开始', top: '35%'}]}
    ])

    // 查询奖品列表
    const queryRaffleAwardListHandle = async () => {
        const result = await queryRaffleAwardList(strategyId);
        const {code, info, data} = await result.json();
        if(code != "0000"){
            window.alert("获取抽奖奖品列表失败 code:" + code + " info:" + info)
            return;
        }
        // 提前定好坐标
        const positions = [
            { x: 0, y: 0 },
            { x: 1, y: 0 },
            { x: 2, y: 0 },
            { x: 2, y: 1 },
            { x: 2, y: 2 },
            { x: 1, y: 2 },
            { x: 0, y: 2 },
            { x: 0, y: 1 }
        ];
        // 创建一个新的奖品数组
        const prizes = data.map((award: RaffleAwardVO, index: number) => {
            const position = positions[index];
            return {
                x: position.x,
                y: position.y,
                fonts: [{id: award.awardId, text: award.awardTitle, top: '35px'}]
            };
        });
        // 设置奖品数据
        setPrizes(prizes)
    }

    // 调用随机抽奖
    const randomRaffleHandle = async () => {
        const result = await randomRaffle(strategyId);
        const {code, info, data} = await result.json();
        if (code != "0000") {
            window.alert("获取抽奖奖品列表失败 code:" + code + " info:" + info)
            return;
        }
        // 为了方便测试，mock 的接口直接返回 awardIndex 也就是奖品列表中第几个奖品。
        return data.awardIndex ? data.awardIndex : prizes.findIndex(prize =>
            //@ts-ignore
            prize.fonts.some(font => font.id === data.awardId)
        ) + 1;
    }

    const [defaultStyle] = useState([{background: "#b8c5f2"}])

    const myLucky = useRef()

    // 页面初始化
    useEffect(() => {
        queryRaffleAwardListHandle().then(r => {
        });
    }, [])
    return <>
        <LuckyGrid
            ref={myLucky}
            width="300px"
            height="300px"
            rows="3"
            cols="3"
            prizes={prizes}
            defaultStyle={defaultStyle}
            buttons={buttons}
            onStart={() => { // 点击抽奖按钮会触发star回调
                // @ts-ignore
                myLucky.current.play()
                setTimeout(() => {
                    // 抽奖接口
                    randomRaffleHandle().then(prizeIndex => {
                            // @ts-ignore
                            myLucky.current.stop(prizeIndex);
                        }
                    );
                }, 2500)
            }}
            onEnd={
                // @ts-ignore
                prize => {
                    alert('恭喜你抽到 ' + prize.fonts[0].text + ' 号奖品')
                }
            }>
        </LuckyGrid>
    </>
}
