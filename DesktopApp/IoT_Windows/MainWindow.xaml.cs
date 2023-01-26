using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Timers;
using System.Security.Cryptography;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using ScottPlot;
using System.Data.Common;
using System.Windows.Markup;
using System.Runtime.CompilerServices;

namespace IoT_Windows
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            // Initialize members
            plotData = new List<double>();
            plotTime = new List<DateTime>();
            plotRoll = new List<double>();
            plotPitch = new List<double>();
            plotYaw = new List<double>();
            plotX = new List<double>();
            plotY = new List<double>();
            plotZ = new List<double>();
            sampleTimeMiliseconds = 300;


            url = "192.168.1.8";
            port = "5000";

            // Initialize autorefresh timer
            dispatcherTimer = new System.Windows.Threading.DispatcherTimer();
            dispatcherTimer.Tick += new EventHandler(dispatcherTimer_Tick);
            dispatcherTimer.Interval = new TimeSpan(0, 0, 0, 0, sampleTimeMiliseconds);

            InitializeComponent();
            tabControl.SelectionChanged += OnTabChange;
        }

        private void OnTabChange(object sender, SelectionChangedEventArgs e)
        {
            dispatcherTimer.Stop();
            plotData.Clear();
            plotTime.Clear();

            plotRoll.Clear();
            plotPitch.Clear();
            plotYaw.Clear();

            plotX.Clear();
            plotY.Clear();
            plotZ.Clear();

            timerEnabled = false;
            TemperatureDataAcquireButton.Content = "Start";
            PressureDataAcquireButton.Content = "Start";
            HumidityDataAcquireButton.Content = "Start";
            OrieentaionDataAcquireButton.Content = "Start";

            if(CurrentTab == "rgbTab")
            {
                RequestHandler();
            }
            
        }

        public static DateTime UnixTimestampToDateTime(double unixTime)
        {
            DateTime unixStart = new DateTime(1970, 1, 1, 0, 0, 0, 0, System.DateTimeKind.Utc);
            long unixTimeStampInTicks = (long)(unixTime * TimeSpan.TicksPerSecond);
            return new DateTime(unixStart.Ticks + unixTimeStampInTicks, System.DateTimeKind.Utc);
        }

        public string CurrentTab
        {
            get
            {
                var selectedTab = (TabItem)tabControl.SelectedItem;
                return selectedTab.Name;
            }
        }

        private void dispatcherTimer_Tick(object sender, EventArgs e)
        {
            RequestHandler();
        }

        private void OnDataAcquireButtonClick(object sender, RoutedEventArgs e)
        {
            if (timerEnabled)
            {
                dispatcherTimer.Stop();
                timerEnabled = false;
                TemperatureDataAcquireButton.Content = "Start";
                PressureDataAcquireButton.Content = "Start";
                HumidityDataAcquireButton.Content = "Start";
                OrieentaionDataAcquireButton.Content = "Start";
                CompassDataAcquireButton.Content = "Start";
                CompassRawDataAcquireButton.Content = "Start";
                AngAccRawDataAcquireButton.Content = "Start";
                AccDataAcquireButton.Content = "Start";
                JoyDataAcquireButton.Content = "Start";
            }
            else
            {
                dispatcherTimer.Start();
                timerEnabled = true;
                TemperatureDataAcquireButton.Content = "Stop";
                PressureDataAcquireButton.Content = "Stop";
                HumidityDataAcquireButton.Content = "Stop";
                OrieentaionDataAcquireButton.Content = "Stop";
                CompassDataAcquireButton.Content = "Stop";
                CompassRawDataAcquireButton.Content = "Stop";
                AngAccRawDataAcquireButton.Content = "Stop";
                AccDataAcquireButton.Content = "Stop";
                JoyDataAcquireButton.Content = "Stop";

            }
        }

        private void OnGetDataButton(object sender, RoutedEventArgs e)
        {
            RequestHandler();
        }

        /* Request handler */
        public void RequestHandler()
        {
            var requestUrl = new StringBuilder("http://" + url + ":" + port);
            switch (CurrentTab)
            {
                case "temperatureTab":
                    requestUrl.Append($"/temperature?unit={temperatureUnit.Text.ToLower()}&source={temperatureSource.Text.ToLower()}");
                    break;

                case "pressureTab":
                    requestUrl.Append($"/pressure?unit={pressureUnit.Text.ToLower()}");
                    break;

                case "humidityTab":
                    requestUrl.Append($"/humidity?unit={humidityUnit.Text.ToLower()}");
                    break;

                case "orientationTab":
                    switch (orientationSource.Text.ToLower())
                    {
                        case "orientation":
                            requestUrl.Append("/orientation?unit=degrees");
                            break;

                        case "accelerometer":
                            requestUrl.Append("/accelerometer");
                            break;

                        case "gyroscope":
                            requestUrl.Append("/gyroscope");
                            break;
                    }
                    break;

                case "compassTab":
                    requestUrl.Append("/compass");
                    break;

                case "compassRawTab":
                    requestUrl.Append("/compass-raw");
                    break;

                case "angAccTab":
                    requestUrl.Append("/gyroscope-raw");
                    break;

                case "accTab":
                    requestUrl.Append("/accelerometer-raw");
                    break;

                case "joyTab":
                    requestUrl.Append("/joystick?mode=data");
                    break;

                case "rgbTab":
                    {
                        for(int x = 0; x <= 7; x++)
                        {
                            for(int y = 0; y <= 7; y++)
                            {
                                requestUrl = new StringBuilder("http://" + url + ":" + port + $"/led-matrix?x={x.ToString()}&y={y.ToString()}");
                                SendGETRequest(requestUrl.ToString());
                            }
                        }
                        return;
                    }

                default:
                    break;
            }
            SendGETRequest(requestUrl.ToString());
        }

        private async void SendGETRequest(string uri)
        {
            using HttpClient client = new();
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/vnd.github.v3+json"));
            client.DefaultRequestHeaders.Add("User-Agent", ".NET Foundation Repository Reporter");

            await ProcessRepositoriesAsync(client, uri);
        }

        async Task ProcessRepositoriesAsync(HttpClient client, string uri)
        {
            string json = "";
            try
            {
                json = await client.GetStringAsync(uri);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An exception occured:\n{ex.Message}.\nThe application will propably crash and burn your computer down :)");
                System.Windows.Application.Current.Shutdown();
            }

            // Parse the received json
            Debug.WriteLine(json);

            switch (CurrentTab)
            {
                case "temperatureTab":
                    {
                        EnviromentalResponse data = JsonSerializer.Deserialize<EnviromentalResponse>(json);
                        plotData.Add(data.value);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] ys = plotData.ToArray();
                        double[] xs = plotTime.Select(x => x.ToOADate()).ToArray();

                        var plt = TemperaturePlot.Plot;

                        plt.Clear();
                        plt.AddScatter(xs, ys);
                        plt.XAxis.DateTimeFormat(true);
                        plt.YLabel($"Temperature [{data.unit}]");
                        plt.XLabel("Time");
                        TemperaturePlot.Refresh();
                        break;
                    }

                case "pressureTab":
                    {
                        EnviromentalResponse data = JsonSerializer.Deserialize<EnviromentalResponse>(json);
                        plotData.Add(data.value);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] ys = plotData.ToArray();
                        double[] xs = plotTime.Select(x => x.ToOADate()).ToArray();

                        var plt = PressurePlot.Plot;

                        plt.Clear();
                        plt.AddScatter(xs, ys);
                        plt.XAxis.DateTimeFormat(true);
                        plt.YLabel($"Pressure [{data.unit}]");
                        plt.XLabel("Time");
                        PressurePlot.Refresh();
                        break;
                    }

                case "humidityTab":
                    {
                        EnviromentalResponse data = JsonSerializer.Deserialize<EnviromentalResponse>(json);
                        plotData.Add(data.value);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] ys = plotData.ToArray();
                        double[] xs = plotTime.Select(x => x.ToOADate()).ToArray();
                        var plt = HumidityPlot.Plot;
                        
                        plt.Clear();
                        plt.AddScatter(xs, ys);
                        plt.XAxis.DateTimeFormat(true);
                        plt.YLabel($"Humidity [{data.unit}]");
                        plt.XLabel("Time");
                        HumidityPlot.Refresh();
                        break;
                    }

                case "orientationTab":
                    {
                        RollPitchYawResponse data = JsonSerializer.Deserialize<RollPitchYawResponse>(json);

                        plotRoll.Add(data.value.roll);
                        plotPitch.Add(data.value.pitch);
                        plotYaw.Add(data.value.yaw);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] yroll = plotRoll.ToArray();
                        double[] yyaw = plotYaw.ToArray();
                        double[] ypitch = plotPitch.ToArray();
                        double[] xs = plotTime.Select(x => x.ToOADate()).ToArray();
                        var plt = OrientationPlot.Plot;

                        plt.Clear();
                        plt.AddScatter(xs, yroll, label: "Roll");
                        plt.AddScatter(xs, yyaw, label: "Yaw");
                        plt.AddScatter(xs, ypitch, label: "Pitch");
                        plt.XAxis.DateTimeFormat(true);
                        plt.YLabel($"RPY [{data.unit}]");
                        plt.XLabel("Time");
                        plt.Legend(true);
                        OrientationPlot.Refresh();
                        break;
                    }

                case "compassTab":
                    {
                        EnviromentalResponse data = JsonSerializer.Deserialize<EnviromentalResponse>(json);
                        plotData.Add(data.value);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] ys = plotData.ToArray();
                        double[] xs = plotTime.Select(x => x.ToOADate()).ToArray();
                        var plt = CompassPlot.Plot;

                        plt.Clear();
                        plt.AddScatter(xs, ys);
                        plt.XAxis.DateTimeFormat(true);
                        plt.YLabel($"North dir [{data.unit}]");
                        plt.XLabel("Time");
                        CompassPlot.Refresh();
                        break;
                    }

                case "compassRawTab":
                    {
                        XYZResponse data = JsonSerializer.Deserialize<XYZResponse>(json);

                        plotX.Add(data.value.x);
                        plotY.Add(data.value.y);
                        plotZ.Add(data.value.z);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] yx = plotX.ToArray();
                        double[] yy = plotY.ToArray();
                        double[] yz = plotZ.ToArray();
                        double[] xs = plotTime.Select(x => x.ToOADate()).ToArray();
                        var plt = CompassRawPlot.Plot;

                        plt.Clear();
                        plt.AddScatter(xs, yx, label: "x");
                        plt.AddScatter(xs, yy, label: "y");
                        plt.AddScatter(xs, yz, label: "z");
                        plt.XAxis.DateTimeFormat(true);
                        plt.YLabel($"XYZ [{data.unit}]");
                        plt.XLabel("Time");
                        plt.Legend(true);
                        CompassRawPlot.Refresh();
                        break;
                    }

                case "angAccTab":
                    {
                        XYZResponse data = JsonSerializer.Deserialize<XYZResponse>(json);

                        plotX.Add(data.value.x);
                        plotY.Add(data.value.y);
                        plotZ.Add(data.value.z);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] yx = plotX.ToArray();
                        double[] yy = plotY.ToArray();
                        double[] yz = plotZ.ToArray();
                        double[] xs = plotTime.Select(x => x.ToOADate()).ToArray();
                        var plt = angAccPlot.Plot;

                        plt.Clear();
                        plt.AddScatter(xs, yx, label: "x");
                        plt.AddScatter(xs, yy, label: "y");
                        plt.AddScatter(xs, yz, label: "z");
                        plt.XAxis.DateTimeFormat(true);
                        plt.YLabel($"XYZ [{data.unit}]");
                        plt.XLabel("Time");
                        plt.Legend(true);
                        angAccPlot.Refresh();
                        break;
                    }

                case "accTab":
                    {
                        XYZResponse data = JsonSerializer.Deserialize<XYZResponse>(json);

                        plotX.Add(data.value.x);
                        plotY.Add(data.value.y);
                        plotZ.Add(data.value.z);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] yx = plotX.ToArray();
                        double[] yy = plotY.ToArray();
                        double[] yz = plotZ.ToArray();
                        double[] xs = plotTime.Select(x => x.ToOADate()).ToArray();
                        var plt = AccPlot.Plot;

                        plt.Clear();
                        plt.AddScatter(xs, yx, label: "x");
                        plt.AddScatter(xs, yy, label: "y");
                        plt.AddScatter(xs, yz, label: "z");
                        plt.XAxis.DateTimeFormat(true);
                        plt.YLabel($"XYZ [{data.unit}]");
                        plt.XLabel("Time");
                        plt.Legend(true);
                        AccPlot.Refresh();
                        break;
                    }

                case "joyTab":
                    {
                        JoystickResponse data = JsonSerializer.Deserialize<JoystickResponse>(json);

                        plotX.Add(data.value.x);
                        plotY.Add(data.value.y);
                        plotTime.Add(UnixTimestampToDateTime(data.time));

                        double[] yx = plotX.ToArray();
                        double[] yy = plotY.ToArray();
                        var plt = JoyPlot.Plot;

                        plt.Clear();
                        plt.AddScatter(yx, yy, label: "pos");
                        plt.YLabel("X");
                        plt.XLabel("Y");
                        JoyPlot.Refresh();
                        break;
                    }

                case "rgbTab":
                    {
                        RGBResponse data = JsonSerializer.Deserialize<RGBResponse>(json);
                        int y = data.value.x;
                        int x = data.value.y;

                        RadioButton selectedLED = ledMatrix.Children.Cast<RadioButton>().First(e => Grid.GetRow(e) == x && Grid.GetColumn(e) == y);
                        var brush = new SolidColorBrush(Color.FromRgb(data.value.rgb[0], data.value.rgb[1], data.value.rgb[2]));
                        selectedLED.Background = brush;
                        break;
                    }
            }
        }

        private async void SetRgbColorClick(object sender, RoutedEventArgs e)
        {
            var checkedValue = ledMatrix.Children.OfType<RadioButton>().FirstOrDefault(r => r.IsChecked.HasValue && r.IsChecked.Value);
            
            int y = Grid.GetRow(checkedValue);
            int x = Grid.GetColumn(checkedValue);

            string address = "http://" + url + ":" + port + $"/led-matrix?x={x}&y={y}&r={(int)redSlider.Value}&g={(int)greenSlider.Value}&b={(int)blueSlider.Value}";
            using HttpClient client = new();
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/vnd.github.v3+json"));
            client.DefaultRequestHeaders.Add("User-Agent", ".NET Foundation Repository Reporter");
            await setLED(client, address);
            RequestHandler();
        }

        async Task setLED(HttpClient client, string uri)
        {
            await client.PutAsync(uri, null);
        }

        /* Server configuration */
        private string url;
        private string port;

        /* Timer */
        System.Windows.Threading.DispatcherTimer dispatcherTimer;
        private int sampleTimeMiliseconds;
        bool timerEnabled = false;

        /* Plot buffer */
        List<double> plotData;
        List<DateTime> plotTime;

        List<double> plotRoll;
        List<double> plotPitch;
        List<double> plotYaw;

        List<double> plotX;
        List<double> plotY;
        List<double> plotZ;

        private void applyButton_Click(object sender, RoutedEventArgs e)
        {
            url = ipBox.Text;
            port = portBox.Text;
            Int32.TryParse(sampleTimeBox.Text, out sampleTimeMiliseconds);
        }
    }

    public class EnviromentalResponse
    {
        public string source { get; set; }
        public int time { get; set; }
        public string unit { get; set; }
        public double value { get; set; }
    }

    public class RollPitchYawResponse
    {
        public string source { get; set; }
        public int time { get; set; }
        public string unit { get; set; }
        public RollPitchYawValue value { get; set; }
    }
    public class RollPitchYawValue
    {
        public double roll { get; set; }
        public double pitch { get; set; }
        public double yaw { get; set; }
    }

    public class XYZResponse
    {
        public string source { get; set; }
        public int time { get; set; }
        public string unit { get; set; }
        public XYZValue value { get; set; }
    }
    
    public class XYZValue
    {
        public double x { get; set; }
        public double y { get; set; }
        public double z { get; set; }
    }

    public class JoystickResponse
    {
        public string source { get; set; }
        public int time { get; set; }
        public string unit { get; set; }
        public JoystickValueResponse value { get; set; }
    }

    public class JoystickValueResponse
    {
        public int clicks { get; set; }
        public int x { get; set; }
        public int y { get; set; }
    }

    public class RGBResponse
    {
        public string source { get; set; }
        public int time { get; set; }
        public string unit { get; set; }
        public RGBValueResponse value { get; set; }
    }

    public class RGBValueResponse
    {
        public int x { get; set; }
        public int y { get; set; }
        public List<byte> rgb { get; set; }
    }




}


// private void testjson()
// {
//     string myJsonResponse = "{\r\n    \"source\": \"temperature-sensor\",\r\n    \"time\": 1674513445,\r\n    \"unit\": \"celcius\",\r\n    \"value\": 41.4375\r\n}";
//     TemperatureResponse deserialisedclass = JsonSerializer.Deserialize<TemperatureResponse>(myJsonResponse);
//     Debug.WriteLine(deserialisedclass.ToString());
// }


// CLEAR ALL STUFF ON UNIT CHANGE