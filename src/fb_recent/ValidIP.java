package fb_recent;

public class ValidIP {
    public String validIPAddress(String IP) {
        if (isValidIPv4(IP)) {
            return "IPv4";
        } else if (isValidIPv6(IP)) {
            return "IPv6";
        } else {
            return "Neither";
        }
    }

    private boolean isValidIPv4(String ip) {
        String[] parts = ip.split("\\.", -1);
        if (parts.length != 4) return false;
        for (String part : parts) {
            if (part.length() > 3 || part.length() == 0) {
                return false;
            }
            if (part.startsWith("0")) {
                if (!(part.length() == 1)) return false;
            }
            try {
                int intValue = Integer.parseInt(part);
                if (intValue < 0 || intValue > 255) return false;
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidIPv6(String ip) {
        String[] parts = ip.split(":", -1);
        if (parts.length != 8) return false;
        for (String part : parts) {
            if (part.length() > 4 || part.length() == 0) {
                return false;
            }
            String trimmedPart = part.replaceAll("[a-fA-F0-9]", "");
            if (trimmedPart.length() != 0) return false;
        }
        return true;
    }

    public static void main(String[] args){
        ValidIP validIp = new ValidIP();
        System.out.println(validIp.validIPAddress("172.16.254.1"));
        System.out.println(validIp.validIPAddress("16.254.1"));
        System.out.println(validIp.validIPAddress("192.168.01.1"));
        System.out.println(validIp.validIPAddress("192.168.1.1."));
        System.out.println(validIp.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334"));
        System.out.println(validIp.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334:"));
        System.out.println(validIp.validIPAddress("256.256.256.256"));
    }
}
