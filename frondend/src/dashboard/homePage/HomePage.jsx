import axios from "axios";
import "./homePage.scss";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  ArcElement,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { useEffect, useRef, useState } from "react";
import { Line, Pie, getElementAtEvent } from "react-chartjs-2";
// import faker from 'faker';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

ChartJS.register(ArcElement, Tooltip, Legend);

const HomePage = () => {
  const [dataStatistics, setDataStatistics] = useState([]);
  const [dataProductOrdered, setDataProductOrdered] = useState([]);
  const [statisticYear, setStatisticYear] = useState([]);
  const [statisticMonth, setStatisticMonth] = useState([]);
  const [monthChosen, setMonthChosen] = useState("");
  const [colorsPie, setColorsPie] = useState([]);

  let statisticsYearRef = useRef();

  const getRandomColor = () => {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    const alpha = Math.random().toFixed(2);

    return `rgba(${r}, ${g}, ${b}, ${alpha})`;
  };

  const optionsLine = {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "THỐNG KÊ DOANH THU NĂM",
      },
    },
  };

  const labelsLine = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
  ];

  const dataLine = {
    labels: labelsLine,
    datasets: statisticYear.map((data) => {
      let color = getRandomColor();
      return {
        label: data.name,
        data: data.prices,
        borderColor: color,
        backgroundColor: color,
      };
    }),
  };

  const dataPie = {
    labels: statisticMonth.map(object => statisticMonth.length > 1 
      ? `${object.name} - SL:${object.quantity}` : object.name),
    datasets: [
      {
        label: "Tổng tiền ",
        data: statisticMonth.map(object => object.prices),
        backgroundColor: colorsPie,
        borderColor: colorsPie,
        borderWidth: 1,
      },
    ],
  };

  useEffect(() => {
    let getApiStatisticOrderDetailOfMonthAndYear = async () => {
      let datas = await axios.get(
        "http://localhost:8080/api/orderdetails/statisticOrderDetailOfMonthAndYear"
      );
      setDataStatistics(datas.data);
    };
    getApiStatisticOrderDetailOfMonthAndYear();
  }, []);

  useEffect(() => {
    let getApiAllNameOfProductOrdered = async () => {
      let datas = await axios.get(
        "http://localhost:8080/api/products/getAllNameOfProductOrdered"
      );
      setDataProductOrdered(datas.data);
    };
    getApiAllNameOfProductOrdered();
  }, []);

  useEffect(() => {
    const output = [];
    const productMap = {};

    for (const item of dataStatistics) {
      const productName = item[1];
      const productData = item.slice(2);

      if (!productMap[productName]) {
        productMap[productName] = {
          nameProduct: productName,
          datas: [],
        };
      }

      productMap[productName].datas.push(productData);
    }

    for (const key in productMap) {
      output.push(productMap[key]);
    }

    const result = output.map((item) => {
      const months = item.datas.map((dataItem) => {
        const date = new Date(dataItem[2]);
        return date.getMonth() + 1;
      });

      let pricesTemp = Array(Math.max(...months)).fill(0);
      item.datas.forEach((dataItem) => {
        return (pricesTemp[Number(dataItem[2].slice(5, 7)) - 1] = dataItem[0]);
      });
      return {
        name: item.nameProduct,
        months: Array.from(new Set(months)),
        prices: pricesTemp,
      };
    });

    setStatisticYear(result);
  }, [JSON.stringify(dataProductOrdered), JSON.stringify(dataStatistics)]);

  useEffect(() => {
    let getApiStatisticMonth = async() => {
      let datas = await axios.get(
        "http://localhost:8080/api/orderdetails/getTotalPricesOfYear"
      );
      console.log(datas.data);
      setStatisticMonth([{
        name : "Tổng Doanh Thu Năm",
        months : [],
        prices : datas.data
      }]);
      setColorsPie([getRandomColor()])
      setMonthChosen("")
    }
    getApiStatisticMonth()
  }, [])

  const onClick = async (event) => {
    if (getElementAtEvent(statisticsYearRef.current, event).length > 0) {
      let monthChosen = getElementAtEvent(statisticsYearRef.current, event)[0].index
      let datas = await axios.get(
        `http://localhost:8080/api/orderdetails/statisticOrderDetailOfMonthAndYearByMonth/${
          monthChosen + 1
        }`
      );
      setMonthChosen(`Tổng Doanh Thu Tháng ${getElementAtEvent(statisticsYearRef.current, event)[0]
        .index + 1} : ${datas.data.map(o => o[2]).reduce((total, value) => total + value, 0).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}`)
      const output = [];
      const productMap = {};

      for (const item of datas.data) {
        const productName = item[1];
        const productData = item.slice(2);

        if (!productMap[productName]) {
          productMap[productName] = {
            nameProduct: productName,
            datas: [],
          };
        }

        productMap[productName].datas.push(productData);
      }

      for (const key in productMap) {
        output.push(productMap[key]);
      }

      const result = output.map((item) => {
        const months = item.datas.map((dataItem) => {
          const date = new Date(dataItem[2]);
          return date.getMonth() + 1;
        });

        let pricesTemp = item.datas.map((dataItem) => {
          return dataItem[0];
        })[0];

        let quantity = item.datas.map((dataItem) => {
          return dataItem[1];
        })[0];

        return {
          name: item.nameProduct,
          months: Array.from(new Set(months)),
          prices: pricesTemp,
          quantity : quantity
        };
      });

      setStatisticMonth(result);
      setColorsPie(
        result.map((object) => {
          return getRandomColor();
        })
      );
    }
    else{
      let datas = await axios.get(
        "http://localhost:8080/api/orderdetails/getTotalPricesOfYear"
      );
      setStatisticMonth([{
        name : "Tổng Doanh Thu Năm",
        months : [],
        prices : datas.data
      }]);
      setColorsPie([getRandomColor()])
      setMonthChosen("")
    }
  };

  return (
    <div className="home-container">
      <div className="statistic-orders">
        <div className="statistic-orders-by-year">
          <Line
            options={optionsLine}
            data={dataLine}
            ref={statisticsYearRef}
            onClick={onClick}
          />
        </div>
        <div className="statistic-orders-by-month">
          <span>{monthChosen}</span>
          <Pie data={dataPie} />
        </div>
      </div>
    </div>
  );
};

export default HomePage;
