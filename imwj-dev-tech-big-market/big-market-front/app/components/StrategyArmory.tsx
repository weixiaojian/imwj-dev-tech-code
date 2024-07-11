import {strategyArmory} from "@/apis";

export function StrategyArmory() {
    const strategyArmoryHandle = async () => {
        let strategyId = 100006;
        if (typeof window !== 'undefined') {
            const queryParams = new URLSearchParams(window.location.search);
            strategyId = Number(queryParams.get('strategyId'));
        }
        if (!strategyId){
            window.alert("请在请求地址中，配置 strategyId 值，如：http://localhost:3000/?strategyId=100006")
            return;
        }
        const res = await strategyArmory(strategyId);
        const {code, info} = await res.json();
        if (code != "0000") {
            window.alert("抽奖策略装配失败 code:" + code + " info:" + info)
            return;
        }
        window.alert("抽奖策略装配成功 code:" + code + " info:" + info)
    }

    return (
        <div
            className="px-6 py-2 mb-8 text-white bg-blue-500 rounded-full shadow-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-300"
            style={{cursor: "pointer"}}
            onClick={strategyArmoryHandle}
        >
            装配抽奖
        </div>
    );
}
