local web = CreateWebUI(0,0,0,0,1,32)
local catched = false

SetWebAlignment(web, 0, 0)
SetWebAnchors(web, 0, 0, 1, 1)
SetWebVisibility(web, WEB_VISIBLE)
SetWebURL(web, "http://asset/java/"..GetPluginHash("OnfireLife").."/ui/index.html")

function VueEmit(event, json)
    ExecuteWebJS(web, "emitVueEvent(\""..event.."\",..."..json..");")
end
AddRemoteEvent("vue:emit", VueEmit)

function IsHeliModel(model)
    local models = { 10, 20, 26, 30, 31, 41 }
    for _,m in ipairs(models) do
        if model == m then
            return true
        end
    end
    return false
end

function SendVehicleHUD()
    local data = { type = "none" }
    local vehicle = GetPlayerVehicle(GetPlayerId())
    if vehicle ~= 0 and GetPlayerVehicleSeat(GetPlayerId()) == 1 then
        data.speed = GetVehicleForwardSpeed(vehicle)
        data.engine = GetVehicleEngineState(vehicle)
        data.light = GetVehicleLightState(vehicle)
        data.fuel = GetVehiclePropertyValue(vehicle, "fuel")
        if IsHeliModel(GetVehicleModel(vehicle)) then
            local x, y, z = GetVehicleLocation(vehicle)
            local pitch, yaw, roll = GetVehicleRotation(vehicle)
            data.type = "heli"
            data.height = z - GetTerrainHeight(x, y, z)
            data.roll = roll
            data.pitch = pitch
            data.heading = yaw
        else
            data.type = "car"
            data.rpm = GetVehicleEngineRPM(vehicle)
            data.gear = GetVehicleGear(vehicle)
            data.horn = IsVehicleHornActive(vehicle)
            data.distance =  GetVehiclePropertyValue(vehicle, "distance")
        end
    end
    VueEmit("vehiclehud:data", json_encode({ data }))
end
CreateTimer(SendVehicleHUD, 200)

function VueCatch()
    SetInputMode(INPUT_GAMEANDUI)
    ShowMouseCursor(true)
    catched = true
end

AddRemoteEvent("vue:catch", VueCatch)

function VueRelease()
    SetInputMode(INPUT_GAME)
    ShowMouseCursor(false)
    catched = false
end
AddEvent("vue:release", VueRelease)
AddRemoteEvent("vue:release", VueRelease)

AddEvent("OnHideMainMenu", function()
    if catched then
        SetInputMode(INPUT_GAMEANDUI)
        ShowMouseCursor(true)
    end
end)

AddEvent("CallRemoteEvent", function(...)
    CallRemoteEvent(...)
end)

function CopyClipboard(text)
    VueEmit("onset:copytoclipboard", "[\""..text.."\"]")
    VueCatch()
end

AddEvent("OnKeyPress", function(key)
    if key == "P" then
        if IsCtrlPressed() then
            local x,y,z = GetPlayerLocation()
            if IsShiftPressed() then
                CopyClipboard(x..", "..y..", "..z..", "..GetPlayerHeading())
            else
                CopyClipboard(x..", "..y..", "..z)
            end
        end
    end
end)