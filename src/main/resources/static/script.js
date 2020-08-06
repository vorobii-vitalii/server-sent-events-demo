const eventSource = new EventSource("/temperature-stream");
const temperatureEvents = document.querySelector('.temperature-events');
const MAX_TEMPERATURE_EVENTS_COUNT_TO_DISPLAY = 7;

const assembleEvent = message => {
    const newEvent  = document.createElement('li');
    newEvent.className = "collection-item";
    const text = document.createElement('h4');
    text.innerText = message;
    newEvent.appendChild(text);
    return newEvent;
};

const addNewEvent = message => {
    if (temperatureEvents.childElementCount > MAX_TEMPERATURE_EVENTS_COUNT_TO_DISPLAY) {
        while (temperatureEvents.childElementCount > 1) {
            temperatureEvents.removeChild(temperatureEvents.lastChild);
        }
    }
    const newEvent = assembleEvent(message);
    temperatureEvents.appendChild(newEvent);
};

eventSource.onopen = () => {
    console.log("Stream is opened");
};

eventSource.onerror = () => {
    console.log("Stream is closed");
};

eventSource.onmessage = e => {
    const parsedObject = JSON.parse(e.data);
    const temperature = Number(parsedObject.value).toFixed(2);
    addNewEvent("Temperature: " + temperature + " C");
};
